package com.github.schmittjoaopedro.rinsim.dvrptwacs;

import com.github.rinde.rinsim.core.model.pdp.Parcel;

public interface DeliveryListener {

    void onParcelDelivered(Parcel parcel);

}
