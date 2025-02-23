package apiengine;

import org.testng.Assert;

import com.afterofficeprisma.model.ResponseItem;
import com.afterofficeprisma.model.request.RequestItem;

public class Assertion {
    public void assertAddProduct(ResponseItem responseItem, RequestItem requestItem) {
        Assert.assertEquals(responseItem.name, requestItem.name);
        Assert.assertEquals(responseItem.dataItem.year, requestItem.dataItem.year);
        Assert.assertEquals(responseItem.dataItem.price, requestItem.dataItem.price);
        Assert.assertEquals(responseItem.dataItem.cpuModel, requestItem.dataItem.cpuModel);
        Assert.assertEquals(responseItem.dataItem.hardDiskSize, requestItem.dataItem.hardDiskSize);
    }
}

