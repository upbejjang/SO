package com.pineone.icbms.so.device.proxy;

import com.pineone.icbms.so.device.entity.Device;
import com.pineone.icbms.so.device.entity.ResultMessage;
import com.pineone.icbms.so.device.util.ClientProfile;
import com.pineone.icbms.so.device.util.ClientService;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DeviceProxy {
    /**
     * SDA 데이터 요청
     * 1. Domain 조회(get)
     * 2. Device By ID 조회(get)
     * 3. Device By Domain 조회(get)
     * 4. Device AspectOperation 조회(post)
     *
     * SI 제어 요청
     * 1, Device 제어(post)
     */

    private ClientService clientService = new ClientService();
    private ObjectMapper mapper = new ObjectMapper();

    /**
     * 1. Domain 조회(get)
     * @param requestUri
     * @return
     */
    public List<String> findDomain(String requestUri){
        //
        List<String> domainList = new ArrayList<>();

        String responseData = clientService.requestGetService(requestUri);

        try {
            domainList = mapper.readValue(responseData, new TypeReference<List<String>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
        return domainList;
    }

    /**
     * 2. Device By ID 조회(get)
     * @param requestUri
     * @return
     */
    public Device findDeviceByID(String requestUri){
        //
        Device device = null;

        String responseData = clientService.requestGetService(requestUri);

        try {
            device = mapper.readValue(responseData,Device.class);
        } catch (Exception e){
            e.printStackTrace();
        }

        return device;
    }

    /**
     * 3. Device By Domain 조회(get)
     * @param requestUri
     * @return
     */
    public List<Device> findDeviceByDomain(String requestUri){
        List<Device> deviceList = new ArrayList<>();

        String responseData = clientService.requestGetService(requestUri);

        try {
            deviceList = mapper.readValue(responseData, new TypeReference<List<Device>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
        return deviceList;
        // 해당 url로 client로 SDA 요청
    }


    /**
     * The actual device control request.
     * @param requestUrl
     * @param requestBody
     * @return
     */
    public String DeviceControlRequest(String requestUrl,String requestBody) {
        //
        ResultMessage resultMessage = new ResultMessage();

        String responseData = clientService.requestPostService(requestUrl, requestBody);
        try {
            resultMessage = mapper.readValue(responseData, ResultMessage.class);
        } catch (IOException e) {
            e.printStackTrace();
            resultMessage.set_resultCode(ClientProfile.RESPONSE_FIALURE_CODE);
        } finally {
            return resultMessage.get_resultCode();
        }
    }

    public Object mappingData(String data,Class T){
        ObjectMapper mapper = new ObjectMapper();
        Object o = null;
        try {
            o = mapper.readValue(data, T);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return o;
    }


}
