package Pages;

import java.util.*;

public class Dummy {

    Map<String,String> map1 = new LinkedHashMap<String,String>();
    Map<String,String> map2 = new LinkedHashMap<String,String>();

    public Map<String, String> returnSimilar(Map<String,String> mapZep,Map<String,String> mapUI){
        Map<String,String> similarResults = new LinkedHashMap<String, String>();
        Set<String> keysOfUI = mapUI.keySet();
        List<String> listKeysOfUi = new ArrayList<String>(keysOfUI);
        for (String aListKeysOfUi : listKeysOfUi) {
            if (mapUI.containsKey(aListKeysOfUi) && mapZep.containsKey(aListKeysOfUi)) {
                if (mapUI.get(aListKeysOfUi).equals(mapZep.get(aListKeysOfUi))) {
                    similarResults.put(aListKeysOfUi, mapUI.get(aListKeysOfUi));
                }
            }
        }
        return similarResults;
    }

    public Map<String, String> returnDifferent(Map<String,String> mapZep,Map<String,String> mapUI)
    {
        Map<String,String> differentResults = new LinkedHashMap<String, String>();
        Set<String> keysOfUI = mapUI.keySet();
        List<String> listKeysOfUi = new ArrayList<String>(keysOfUI);
        for (String aListKeysOfUi : listKeysOfUi) {
            if (mapUI.containsKey(aListKeysOfUi) && mapZep.containsKey(aListKeysOfUi)) {
                if (!mapUI.get(aListKeysOfUi).equals(mapZep.get(aListKeysOfUi))) {
                    differentResults.put(aListKeysOfUi, mapUI.get(aListKeysOfUi));
                }
            }
        }
        return differentResults;
    }
}
