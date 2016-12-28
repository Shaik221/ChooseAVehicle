package com.test.mycompany.chooseavechile.model.module;

import com.test.mycompany.chooseavechile.util.CustomScope;
import com.test.mycompany.chooseavechile.view.contract.ItemsContract;

import dagger.Module;
import dagger.Provides;

@Module
public class ItemModule {
    private final ItemsContract.View mView;

    public ItemModule(ItemsContract.View mView) {
        this.mView = mView;
    }

    @Provides
    @CustomScope
    ItemsContract.View providesItemsContractView() {
        return mView;
    }
}
