package com.myapplication.view;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Cell;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.myapplication.model.Chamber;
import com.myapplication.model.Rent;
import com.myapplication.R;
import com.myapplication.model.Tenant;
import com.myapplication.viewmodel.ChamberViewModel;
import com.myapplication.viewmodel.RentViewModel;
import com.myapplication.viewmodel.TenantViewModel;
import androidx.lifecycle.Observer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.HashMap;
import java.util.Map;
import android.os.Handler;
import android.os.Looper;

public class RentActivity extends AppCompatActivity {
    private EditText etSearch;
    private Button btnSearch;
    private RecyclerView recyclerView;
    private FloatingActionButton btnAdd, btnPdf;
    private RentAdapter rentAdapter;
    private List<Rent> rentList;
    private RentViewModel rentViewModel;
    private ChamberViewModel chamberViewModel;
    private TenantViewModel tenantViewModel;
    private List<Tenant> tenants = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent);
        getSupportActionBar().setTitle("Gestión de Alquileres");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.rvRentals);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        etSearch = findViewById(R.id.etSearch);
        btnSearch = findViewById(R.id.btnSearch);
        btnAdd = findViewById(R.id.btnAdd);
        btnPdf = findViewById(R.id.btnPdf);

        rentViewModel =  new ViewModelProvider(this).get(RentViewModel.class);
        chamberViewModel = new ViewModelProvider(this).get(ChamberViewModel.class);
        tenantViewModel = new ViewModelProvider(this).get(TenantViewModel.class);
        //chamberViewModel.insert(new Chamber("A1"));
        //chamberViewModel.insert(new Chamber("A2"));
        //chamberViewModel.insert(new Chamber("A3"));
        rentViewModel.getAllRents().observe(this, new Observer<List<Rent>>() {
            @Override
            public void onChanged(List<Rent> rents) {
                rentAdapter = new RentAdapter(RentActivity.this, rents, rentViewModel,
                        tenantViewModel, chamberViewModel);
                recyclerView.setAdapter(rentAdapter);
                rentList = rents;
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchRentals(etSearch.getText().toString());
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewRental();
            }
        });

        btnPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                            generatePDF(rentList);
                    }
                }).start();
            }
        });

    }
    private void searchRentals(String query) {
        // Implementar lógica de búsqueda
    }
    private void addNewRental() {
        Intent intent = new Intent(this, RentCreateActivity.class);
        startActivity(intent);
    }
    private void message(String text){
        Toast.makeText(RentActivity.this, text, Toast.LENGTH_LONG).show();
    }

    public void generatePDF(List<Rent> rents) {
        String pdfPath = getExternalFilesDir(null).toString() + "/rents.pdf";
        ExecutorService executor = Executors.newFixedThreadPool(2);
        CountDownLatch latch = new CountDownLatch(rents.size() * 2);
        Map<Integer, Tenant> tenantMap = new HashMap<>();
        Map<Integer, Chamber> chamberMap = new HashMap<>();
        Handler mainHandler = new Handler(Looper.getMainLooper());

        try {
            PdfWriter writer = new PdfWriter(pdfPath);
            PdfDocument pdfDocument = new PdfDocument(writer);
            Document document = new Document(pdfDocument);

            float[] columnWidths = {1, 2, 3, 4, 5, 6};
            Table table = new Table(columnWidths);

            table.addCell(new Cell().add(new Paragraph("Fecha inicial")));
            table.addCell(new Cell().add(new Paragraph("Fecha final")));
            table.addCell(new Cell().add(new Paragraph("Monto")));
            table.addCell(new Cell().add(new Paragraph("Inquilino")));
            table.addCell(new Cell().add(new Paragraph("Habitación")));
            table.addCell(new Cell().add(new Paragraph("Estado")));

            for (Rent rent : rents) {
                executor.execute(() -> {
                    LiveData<Tenant> liveData = tenantViewModel.getTenantById(rent.getTenantId());
                    mainHandler.post(() -> {
                        liveData.observeForever(new Observer<Tenant>() {
                            @Override
                            public void onChanged(Tenant tenant) {
                                if (tenant != null) {
                                    tenantMap.put(rent.getTenantId(), tenant);
                                }
                                latch.countDown();
                            }
                        });
                    });
                });

                executor.execute(() -> {
                    LiveData<Chamber> liveData = chamberViewModel.getChamberById(rent.getChamberId());
                    mainHandler.post(() -> {
                        liveData.observeForever(new Observer<Chamber>() {
                            @Override
                            public void onChanged(Chamber chamber) {
                                if (chamber != null) {
                                    chamberMap.put(rent.getChamberId(), chamber);
                                }
                                latch.countDown();
                            }
                        });
                    });
                });
            }

            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            for (Rent rent : rents) {
                String state = (rent.getState() == 1) ? "Activo" : "Concluido";

                table.addCell(new Cell().add(new Paragraph(rent.getStartDate())));
                table.addCell(new Cell().add(new Paragraph(rent.getEndDate())));
                table.addCell(new Cell().add(new Paragraph(String.valueOf(rent.getPrice()))));
                Tenant tenant = tenantMap.get(rent.getTenantId());
                if (tenant != null) {
                    table.addCell(new Cell().add(new Paragraph(tenant.getDni())));
                } else {
                    table.addCell(new Cell().add(new Paragraph("N/A")));
                }
                Chamber chamber = chamberMap.get(rent.getChamberId());
                if (chamber != null) {
                    table.addCell(new Cell().add(new Paragraph(chamber.getName())));
                } else {
                    table.addCell(new Cell().add(new Paragraph("N/A")));
                }
                table.addCell(new Cell().add(new Paragraph(state)));
            }

            executor.shutdown();

            document.add(table);
            document.close();

            runOnUiThread(() -> Toast.makeText(this, "PDF generated at " + pdfPath, Toast.LENGTH_SHORT).show());
        } catch (Exception e) {
            e.printStackTrace();
            runOnUiThread(() -> Toast.makeText(this, "Error generating PDF: " + e.getMessage(), Toast.LENGTH_SHORT).show());
        }
    }

    @Override
    public boolean onSupportNavigateUp(){
        getOnBackPressedDispatcher().onBackPressed();
        return super.onSupportNavigateUp();
    }
}