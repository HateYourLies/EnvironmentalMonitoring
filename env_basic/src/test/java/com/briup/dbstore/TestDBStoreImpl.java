package com.briup.dbstore;

import com.briup.gather.GatherImpl;
import org.apache.commons.beanutils.BeanUtils;
import org.junit.Test;


public class TestDBStoreImpl {
    @Test
    public void test(){
        DBStoreImpl dbStore = new DBStoreImpl();
        GatherImpl gather = new GatherImpl();
        dbStore.insertData(gather.gatherData());

    }
}
