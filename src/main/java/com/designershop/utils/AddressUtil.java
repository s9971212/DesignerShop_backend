package com.designershop.utils;

import com.designershop.exceptions.OrderException;
import org.apache.commons.lang3.StringUtils;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class AddressUtil {

    public static List<Map<String, Object>> loadDistrictsFromYaml() {
        InputStream inputStream = AddressUtil.class.getClassLoader().getResourceAsStream("addresses.yml");
        Map<String, List<Map<String, Object>>> data = new Yaml().load(inputStream);
        return data.get("nations");
    }

    public static String validateAddress(String district, String city, String state, String postalCode, String nation) throws OrderException {
        List<Map<String, Object>> nations = loadDistrictsFromYaml();
        for (Map<String, Object> nationData : nations) {
            if (StringUtils.equals(nation, (String) nationData.get("name"))) {
                Map<String, Object> stateData = validateLocation(state, nationData, "states");
                Map<String, Object> cityData = validateLocation(city, stateData, "cities");
                Map<String, Object> districtData = validateLocation(district, cityData, "districts");
                String validatedPostalCode = validatePostalCode(postalCode, stateData, cityData, districtData);

                return Stream.of(validatedPostalCode, nation, state, city, district).filter(StringUtils::isNotBlank).collect(Collectors.joining(""));
            }
        }

        throw new OrderException("地址格式錯誤");
    }

    @SuppressWarnings("unchecked")
    private static Map<String, Object> validateLocation(String location, Map<String, Object> data, String type) throws OrderException {
        List<Map<String, Object>> locations = (List<Map<String, Object>>) data.get(type);
        if ((StringUtils.isBlank(location) && Objects.nonNull(locations)) || (StringUtils.isNotBlank(location) && Objects.isNull(locations))) {
            throw new OrderException("地址格式錯誤");
        }

        if (StringUtils.isNotBlank(location)) {
            return locations.stream().filter(locData -> StringUtils.equals(location, (String) locData.get("name"))).findFirst()
                    .orElseThrow(() -> new OrderException("地址格式錯誤"));
        }

        return data;
    }

    private static String validatePostalCode(String postalCode, Map<String, Object> stateData, Map<String, Object> cityData, Map<String, Object> districtData) {
        String validatedPostalCode = Stream.of(stateData, cityData, districtData).filter(data -> data.containsKey("postalCode"))
                .map(data -> Integer.toString((Integer) data.get("postalCode"))).findFirst()
                .orElse("");

        return StringUtils.isNotBlank(postalCode) && postalCode.startsWith(validatedPostalCode) ? postalCode : validatedPostalCode;
    }
}
