package com.myapplication.onClickInterface;

import com.myapplication.model.PropertyModel;

public interface OnClickItemInterfaceProperty {

    void onClickItem(PropertyModel propertyModel, boolean isEdit);

    void onClickItemDeactive(PropertyModel propertyModel);
}
