package com.designershop.orders;

import com.designershop.carts.models.ReadCartItemResponseModel;
import com.designershop.entities.*;
import com.designershop.exceptions.*;
import com.designershop.orders.models.CreateOrderDeliveryRequestModel;
import com.designershop.orders.models.ReadOrderDeliveryResponseModel;
import com.designershop.repositories.*;
import com.designershop.utils.AddressUtil;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class OrderDeliveriesService {

    private final HttpSession session;
    private final OrderDeliveryRepository orderDeliveryRepository;

    @Transactional
    public String createOrderDelivery(CreateOrderDeliveryRequestModel request) throws EmptyException, UserException, OrderException {
        String address = request.getAddress();
        String district = request.getDistrict();
        String city = request.getCity();
        String state = request.getState();
        String postalCode = request.getPostalCode();
        String nation = request.getNation();
        String contactPhone = request.getContactPhone();
        String contactName = request.getContactName();
        String defaultCheckBox = request.getDefaultCheckBox();

        if (StringUtils.isBlank(address) || StringUtils.isBlank(nation) || StringUtils.isBlank(contactPhone)
                || StringUtils.isBlank(contactName) || StringUtils.isBlank(defaultCheckBox)) {
            throw new EmptyException("地址、聯絡電話、聯絡人姓名與設為預設地址不得為空");
        }

        if (!contactPhone.matches("^09\\d{8}$")) {
            throw new OrderException("聯絡電話格式錯誤");
        }

        UserProfile userProfile = (UserProfile) session.getAttribute("userProfile");
        if (Objects.isNull(userProfile)) {
            throw new UserException("此帳戶未登入，請重新確認");
        }

        String validatedAddress = AddressUtil.validateAddress(district, city, state, postalCode, nation);
        Matcher matcher = Pattern.compile("\\d+").matcher(validatedAddress);
        if (matcher.find()) {
            postalCode = matcher.group();
        }

        boolean isDefault = StringUtils.equals("Y", defaultCheckBox);
        if (isDefault) {
            OrderDelivery orderDelivery = orderDeliveryRepository.findByIsDefaultAndUserId(userProfile.getUserId());
            if (Objects.nonNull(orderDelivery)) {
                orderDelivery.setDefault(false);
                orderDeliveryRepository.save(orderDelivery);
            }
        }

        OrderDelivery orderDeliveryCreate = new OrderDelivery();
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

        return String.join("", validatedAddress, address);
    }

    @Transactional
    public List<ReadOrderDeliveryResponseModel> readAllOrderDelivery() throws UserException, OrderException {
        UserProfile userProfile = (UserProfile) session.getAttribute("userProfile");
        if (Objects.isNull(userProfile)) {
            throw new UserException("此帳戶未登入，請重新確認");
        }

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
}
