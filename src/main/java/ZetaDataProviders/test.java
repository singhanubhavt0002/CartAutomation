package ZetaDataProviders;

import com.jayway.jsonpath.JsonPath;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class test {
    @DataProvider(name="getData")
    public Object[][] getDataFromJson() throws IOException {
       File file = new File("src/main/java/ZetaDataProviders/data.json");

        List<String> names = JsonPath.read(file,"$..name");
                List<Integer> ids = JsonPath.read(file,"$..id");
                        Object[][] data = new Object[names.size()][2];

        for(int i=0;i<names.size();i++) {
            data[i][0] = names.get(i);
            data[i][1] = ids.get(i);
        }
            return data;


    }

        @Test(dataProvider="getData")
        public void getPersonalDetails(String name, int id){
            System.out.println("Name:"+name);
            System.out.println("Id:"+id);
        }
}