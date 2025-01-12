package com.designershop.orders;

import com.designershop.entities.OrderDelivery;
import com.designershop.entities.UserProfile;
import com.designershop.exceptions.EmptyException;
import com.designershop.exceptions.OrderException;
import com.designershop.orders.models.CreateOrderDeliveryRequestModel;
import com.designershop.orders.models.ReadOrderDeliveryResponseModel;
import com.designershop.orders.models.UpdateOrderDeliveryRequestModel;
import com.designershop.repositories.OrderDeliveryRepository;
import com.designershop.utils.AddressUtil;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Ivan Wang
 * @version 1.0
 * @date 2024/12/22
 */
@Service
@RequiredArgsConstructor
public class OrderDeliveryService {

    private final HttpSession session;
    private final OrderDeliveryRepository orderDeliveryRepository;

    public String createOrderDelivery(CreateOrderDeliveryRequestModel request) throws EmptyException, OrderException {
        String address = request.getAddress();
        String district = request.getDistrict();
        String city = request.getCity();
        String state = request.getState();
        String postalCode = request.getPostalCode();
        String nation = request.getNation();
        String contactPhone = request.getContactPhone();
        String contactName = request.getContactName();
        String defaultCheckBox = request.getDefaultCheckBox();

        if (StringUtils.isBlank(address) || StringUtils.isBlank(nation) || StringUtils.isBlank(contactPhone) || StringUtils.isBlank(contactName)
                || StringUtils.isBlank(defaultCheckBox)) {
            throw new EmptyException("地址、聯絡電話、聯絡人姓名與設為預設地址不得為空");
        }
        if (!contactPhone.matches("^0?9\\d{8}$")) {
            throw new OrderException("聯絡電話格式錯誤");
        }

        UserProfile userProfile = (UserProfile) session.getAttribute("userProfile");
        String validatedAddress = AddressUtil.validateAddress(district, city, state, postalCode, nation);
        String fullAddress = String.join("", validatedAddress, address);
        Matcher matcher = Pattern.compile("^\\d+").matcher(validatedAddress);
        if (matcher.find()) {
            postalCode = matcher.group();
        }

        OrderDelivery orderDelivery = orderDeliveryRepository.findByIsDefaultAndUserId(userProfile.getUserId());
        boolean isDefault = Objects.isNull(orderDelivery) || StringUtils.equals("Y", defaultCheckBox);
        if (Objects.nonNull(orderDelivery) && isDefault) {
            orderDelivery.setDefault(false);
            orderDeliveryRepository.save(orderDelivery);
        }

        OrderDelivery orderDeliveryCreate = new OrderDelivery();
        orderDeliveryCreate.setFullAddress(fullAddress);
        orderDeliveryCreate.setAddress(address);
        orderDeliveryCreate.setDistrict(district);
        orderDeliveryCreate.setCity(city);
        orderDeliveryCreate.setState(state);
        orderDeliveryCreate.setPostalCode(postalCode);
        orderDeliveryCreate.setNation(nation);
        orderDeliveryCreate.setContactPhone(contactPhone);
        orderDeliveryCreate.setContactName(contactName);
        orderDeliveryCreate.setDefault(isDefault);
        orderDeliveryCreate.setUserId(userProfile.getUserId());
        orderDeliveryRepository.save(orderDeliveryCreate);
        return fullAddress;
    }

    public List<ReadOrderDeliveryResponseModel> readAllOrderDelivery() throws OrderException {
        UserProfile userProfile = (UserProfile) session.getAttribute("userProfile");
        List<ReadOrderDeliveryResponseModel> response = new ArrayList<>();
        List<OrderDelivery> orderDeliveryList = orderDeliveryRepository.findAllByUserId(userProfile.getUserId());
        for (OrderDelivery orderDelivery : orderDeliveryList) {
            ReadOrderDeliveryResponseModel readOrderDeliveryResponseModel = new ReadOrderDeliveryResponseModel();
            BeanUtils.copyProperties(orderDelivery, readOrderDeliveryResponseModel);
            readOrderDeliveryResponseModel.setDeliveryId(Integer.toString(orderDelivery.getDeliveryId()));
            readOrderDeliveryResponseModel.setIsDefault(orderDelivery.isDefault() ? "Y" : "N");

            response.add(readOrderDeliveryResponseModel);
        }

        return response;
    }

    public ReadOrderDeliveryResponseModel readOrderDelivery(String deliveryId) throws OrderException {
        OrderDelivery orderDelivery = orderDeliveryRepository.findByDeliveryId(Integer.parseInt(deliveryId));
        if (Objects.isNull(orderDelivery)) {
            throw new OrderException("此訂單配送不存在，請重新確認");
        }

        ReadOrderDeliveryResponseModel response = new ReadOrderDeliveryResponseModel();
        BeanUtils.copyProperties(orderDelivery, response);
        response.setDeliveryId(Integer.toString(orderDelivery.getDeliveryId()));
        response.setIsDefault(orderDelivery.isDefault() ? "Y" : "N");
        return response;
    }

    public ReadOrderDeliveryResponseModel readOrderDeliveryByIsDefault() throws OrderException {
        UserProfile userProfile = (UserProfile) session.getAttribute("userProfile");
        OrderDelivery orderDelivery = orderDeliveryRepository.findByIsDefaultAndUserId(userProfile.getUserId());
        if (Objects.isNull(orderDelivery)) {
            throw new OrderException("此訂單配送不存在，請重新確認");
        }

        ReadOrderDeliveryResponseModel response = new ReadOrderDeliveryResponseModel();
        BeanUtils.copyProperties(orderDelivery, response);
        response.setDeliveryId(Integer.toString(orderDelivery.getDeliveryId()));
        response.setIsDefault(orderDelivery.isDefault() ? "Y" : "N");
        return response;
    }

    public String updateOrderDelivery(String deliveryId, UpdateOrderDeliveryRequestModel request) throws EmptyException, OrderException {
        String address = request.getAddress();
        String district = request.getDistrict();
        String city = request.getCity();
        String state = request.getState();
        String postalCode = request.getPostalCode();
        String nation = request.getNation();
        String contactPhone = request.getContactPhone();
        String contactName = request.getContactName();
        String defaultCheckBox = request.getDefaultCheckBox();

        if (StringUtils.isBlank(deliveryId) || StringUtils.isBlank(address) || StringUtils.isBlank(nation) || StringUtils.isBlank(contactPhone)
                || StringUtils.isBlank(contactName) || StringUtils.isBlank(defaultCheckBox)) {
            throw new EmptyException("地址、聯絡電話、聯絡人姓名與設為預設地址不得為空");
        }
        if (!contactPhone.matches("^0?9\\d{8}$")) {
            throw new OrderException("聯絡電話格式錯誤");
        }
        OrderDelivery orderDelivery = orderDeliveryRepository.findByDeliveryId(Integer.parseInt(deliveryId));
        if (Objects.isNull(orderDelivery)) {
            throw new OrderException("此訂單配送不存在，請重新確認");
        }

        UserProfile userProfile = (UserProfile) session.getAttribute("userProfile");
        String validatedAddress = AddressUtil.validateAddress(district, city, state, postalCode, nation);
        String fullAddress = String.join("", validatedAddress, address);
        Matcher matcher = Pattern.compile("^\\d+").matcher(validatedAddress);
        if (matcher.find()) {
            postalCode = matcher.group();
        }

        OrderDelivery orderDeliveryDefault = orderDeliveryRepository.findByIsDefaultAndUserId(userProfile.getUserId());
        boolean isDefault = orderDelivery.isDefault() || StringUtils.equals("Y", defaultCheckBox);
        if (!orderDelivery.isDefault() && isDefault) {
            orderDeliveryDefault.setDefault(false);
            orderDeliveryRepository.save(orderDeliveryDefault);
        }

        orderDelivery.setFullAddress(fullAddress);
        orderDelivery.setAddress(address);
        orderDelivery.setDistrict(district);
        orderDelivery.setCity(city);
        orderDelivery.setState(state);
        orderDelivery.setPostalCode(postalCode);
        orderDelivery.setNation(nation);
        orderDelivery.setContactPhone(contactPhone);
        orderDelivery.setContactName(contactName);
        orderDelivery.setDefault(isDefault);
        orderDelivery.setUserId(userProfile.getUserId());
        orderDeliveryRepository.save(orderDelivery);
        return fullAddress;
    }

    public String deleteOrderDelivery(String deliveryId) throws OrderException {
        OrderDelivery orderDelivery = orderDeliveryRepository.findByDeliveryId(Integer.parseInt(deliveryId));
        if (Objects.isNull(orderDelivery)) {
            throw new OrderException("此訂單配送不存在，請重新確認");
        }

        UserProfile userProfile = (UserProfile) session.getAttribute("userProfile");
        orderDeliveryRepository.delete(orderDelivery);
        return userProfile.getUserId();
    }
}
