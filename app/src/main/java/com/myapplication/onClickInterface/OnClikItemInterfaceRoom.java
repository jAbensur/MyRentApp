package com.myapplication.onClickInterface;

import com.myapplication.model.RoomModel;

public interface OnClikItemInterfaceRoom {

    void onClickItem(RoomModel roomModel, boolean isEdit);

    void onClickItemDeactive(RoomModel roomModel);

    void onClickItemInfo(RoomModel roomModel);
}
