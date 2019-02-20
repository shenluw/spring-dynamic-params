package top.shenluw.sldp;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Shenluw
 * 创建日期：2019/2/20 17:28
 */
public class TypeNameAliasResolver {

    private Map<String, String> aliasMap = new HashMap<>(32);

    public TypeNameAliasResolver(Map<String, String> aliasMap) {
        this.aliasMap.putAll(aliasMap);
    }

    public String resolver(String origin) {
        return aliasMap.getOrDefault(origin, origin);
    }

    public void add(String alias, String type) {
        aliasMap.put(alias, type);
    }

    public void add(Map<String, String> map) {
        aliasMap.putAll(map);
    }

}
