/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.structural.facade;

import java.util.ArrayList;
import java.util.List;

/**
 * @author rolex
 * @since 2020
 */
public class GovernmentFacade {

    List<Government> governments;

    public GovernmentFacade() {
        governments = new ArrayList<>();
        governments.add(new IndustryAndCommerceBureau());
        governments.add(new TaxBureau());
        governments.add(new UrbanManagementAndLawEnforcement());
    }

    public void approve(){
        governments.forEach(government -> government.approve());
    }

}
