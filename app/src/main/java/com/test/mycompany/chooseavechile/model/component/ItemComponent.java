package com.test.mycompany.chooseavechile.model.component;


import com.test.mycompany.chooseavechile.model.module.ItemModule;
import com.test.mycompany.chooseavechile.util.CustomScope;
import com.test.mycompany.chooseavechile.view.CarManufacturesListFragment;

import dagger.Component;

@CustomScope
@Component(dependencies = NetComponent.class, modules = ItemModule.class)
public interface ItemComponent {
    void inject(CarManufacturesListFragment activity);
}

