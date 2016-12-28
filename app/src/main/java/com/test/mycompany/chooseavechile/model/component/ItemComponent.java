package com.test.mycompany.chooseavechile.model.component;


import com.test.mycompany.chooseavechile.model.module.ItemModule;
import com.test.mycompany.chooseavechile.util.CustomScope;
import com.test.mycompany.chooseavechile.view.MainActivity;

import dagger.Component;

/**
 * Created by rra7y33 on 11/08/2016.
 */
@CustomScope
@Component(dependencies = NetComponent.class, modules = ItemModule.class)
public interface ItemComponent {
    void inject(MainActivity activity);
}

