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
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.myapplication.model.Rent;
import com.myapplication.R;
import com.myapplication.model.Room;
import com.myapplication.model.Tenant;
import com.myapplication.viewmodel.RentViewModel;
import com.myapplication.viewmodel.RoomViewModel;
import com.myapplication.viewmodel.TenantViewModel;
import androidx.lifecycle.Observer;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;

public class RentActivity extends AppCompatActivity {
    private EditText etSearch;
    private RecyclerView recyclerView;
    private RentAdapter rentAdapter;
    private List<Rent> rentList;
    private RentViewModel rentViewModel;
    private TenantViewModel tenantViewModel;
    private RoomViewModel roomViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Gestión de Alquileres");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.rvRentals);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        etSearch = findViewById(R.id.etSearch);
        Button btnSearch = findViewById(R.id.btnSearch);
        FloatingActionButton btnAdd = findViewById(R.id.btnAdd);
        FloatingActionButton btnPdf = findViewById(R.id.btnPdf);

        rentViewModel =  new ViewModelProvider(this).get(RentViewModel.class);
        roomViewModel = new ViewModelProvider(this).get(RoomViewModel.class);
        tenantViewModel = new ViewModelProvider(this).get(TenantViewModel.class);

        rentViewModel.getAllRents().observe(this, new Observer<List<Rent>>() {
            @Override
            public void onChanged(List<Rent> rents) {
                rentAdapter = new RentAdapter(RentActivity.this, rents, rentViewModel,
                        tenantViewModel, roomViewModel);
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
        if (query.isEmpty()) {
            rentAdapter.updateRentList(rentList);
        } else {
            List<Rent> filteredList = new ArrayList<>();
            for (Rent rent : rentList) {
                boolean matchesQuery = false;

                if (rent.getStartDate().toLowerCase().contains(query.toLowerCase()) ||
                        rent.getEndDate().toLowerCase().contains(query.toLowerCase()) ||
                        String.valueOf(rent.getPrice()).contains(query) ||
                        (rent.getState() == 1 ? "Activo" : "Concluido").toLowerCase().contains(query.toLowerCase())) {
                    matchesQuery = true;
                } else {
                    Tenant tenant = tenantViewModel.getTenntById(rent.getTenantId()).getValue();
                    Room room = roomViewModel.getRoomById(rent.getChamberId()).getValue();

                    if (tenant != null && tenant.getDni().toLowerCase().contains(query.toLowerCase())) {
                        matchesQuery = true;
                    } else if (room != null && room.getNameR().toLowerCase().contains(query.toLowerCase())) {
                        matchesQuery = true;
                    }
                }

                if (matchesQuery) {
                    filteredList.add(rent);
                }
            }
            rentAdapter.updateRentList(filteredList);
        }
    }
    private void addNewRental() {
        Intent intent = new Intent(this, RentCreateActivity.class);
        startActivity(intent);
    }
    private void message(String text){
        Toast.makeText(RentActivity.this, text, Toast.LENGTH_LONG).show();
    }

    public void generatePDF(List<Rent> rents) {
        String pdfPath = Objects.requireNonNull(getExternalFilesDir(null)).toString() + "/rents.pdf";

        ExecutorService executorService = Executors.newFixedThreadPool(4);
        List<Future<Map<String, String>>> futures = new ArrayList<>();

        try {
            for (Rent rent : rents) {
                futures.add(executorService.submit(() -> {
                    Map<String, String> rentDetails = new HashMap<>();
                    rentDetails.put("startDate", rent.getStartDate());
                    rentDetails.put("endDate", rent.getEndDate());
                    rentDetails.put("price", String.valueOf(rent.getPrice()));
                    rentDetails.put("state", (rent.getState() == 1) ? "Activo" : "Concluido");

                    Tenant tenant = tenantViewModel.getTenntById(rent.getTenantId()).getValue();
                    Room room = roomViewModel.getRoomById(rent.getChamberId()).getValue();

                    rentDetails.put("tenantDni", tenant != null ? tenant.getDni() : "N/A");
                    rentDetails.put("roomName", room != null ? room.getNameR() : "N/A");

                    return rentDetails;
                }));
            }

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

            for (Future<Map<String, String>> future : futures) {
                Map<String, String> rentDetails = future.get();
                table.addCell(new Cell().add(new Paragraph(rentDetails.get("startDate"))));
                table.addCell(new Cell().add(new Paragraph(rentDetails.get("endDate"))));
                table.addCell(new Cell().add(new Paragraph(rentDetails.get("price"))));
                table.addCell(new Cell().add(new Paragraph(rentDetails.get("tenantDni"))));
                table.addCell(new Cell().add(new Paragraph(rentDetails.get("roomName"))));
                table.addCell(new Cell().add(new Paragraph(rentDetails.get("state"))));
            }

            document.add(table);
            document.close();

            runOnUiThread(() -> Toast.makeText(this, "PDF generated at " + pdfPath, Toast.LENGTH_SHORT).show());
        } catch (Exception e) {
            runOnUiThread(() -> Toast.makeText(this, "Error generating PDF: " + e.getMessage(), Toast.LENGTH_SHORT).show());
        } finally {
            executorService.shutdown();
        }
    }

    @Override
    public boolean onSupportNavigateUp(){
        getOnBackPressedDispatcher().onBackPressed();
        return super.onSupportNavigateUp();
    }
}