package com.adex.codingchallenge;

import static org.junit.jupiter.api.Assertions.assertEquals;
import com.adex.codingchallenge.controller.CustomerController;
import com.adex.codingchallenge.model.Customer;
import com.adex.codingchallenge.model.IpBlacklist;
import com.adex.codingchallenge.model.UaBlacklist;
import com.adex.codingchallenge.service.CustomerService;
import com.adex.codingchallenge.service.HourlyStatsService;
import com.adex.codingchallenge.service.IpBlacklistService;
import com.adex.codingchallenge.service.UaBlacklistService;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService; 

    @MockBean
    private IpBlacklistService ipblacklistService; 

    @MockBean
    private UaBlacklistService uablacklistService; 

    @MockBean
    private HourlyStatsService hourlyStatsService; 

    //request params
    private int customerId = 1;
    private String customerName = "customer1";
    private int tagId = 2;
    private String userId = "aaaaaaaa-bbbb-cccc-1111-222222222222";
    private String remoteIp = "123.234.56.78";
    private String userAgent = "Googlebot";
    private long timestamp = 1500000000;

    @Test
    public void testValidRequest() throws Exception{        

        //mock dependencies
        Customer customer = getDummyCustomer(customerId, customerName, 1); //mock customer
        Mockito.when(customerService.getCustomerById(customerId)).thenReturn(customer); //mock customer service
        Mockito.when(ipblacklistService.isIpBlacklisted(getDummyIpBlacklist(remoteIp))).thenReturn(false); //mock IP blacklist service
        Mockito.when(uablacklistService.isUaBlacklisted(getDummyUaBlacklist(userAgent))).thenReturn(false); //mock UA blacklist service
        Mockito.doNothing().when(hourlyStatsService).incrementValidRequests(customerId, timestamp); //mock stats service
        Mockito.doNothing().when(hourlyStatsService).incrementInvalidRequests(customerId, timestamp); //mock stats service

        //build request
        String requstBody = getPayloadString(customerId, true, tagId, true, userId, true, remoteIp, true, timestamp, true);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
        .post("/customer")
        .content(requstBody)
        .accept(MediaType.ALL)
        .contentType(MediaType.APPLICATION_JSON)
        .header("User-Agent", userAgent);

        //send request and get result
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        //assert that returned 200 OK
        assertEquals(HttpStatus.OK.value(), response.getStatus());

    }

    @Test
    public void testInvalidRequestNoCustomerId() throws Exception{

        //mock dependencies
        Customer customer = getDummyCustomer(customerId, customerName, 1); //mock customer
        Mockito.when(customerService.getCustomerById(customerId)).thenReturn(customer); //mock customer service
        Mockito.when(ipblacklistService.isIpBlacklisted(getDummyIpBlacklist(remoteIp))).thenReturn(false); //mock IP blacklist service
        Mockito.when(uablacklistService.isUaBlacklisted(getDummyUaBlacklist(userAgent))).thenReturn(false); //mock UA blacklist service
        Mockito.doNothing().when(hourlyStatsService).incrementValidRequests(customerId, timestamp); //mock stats service
        Mockito.doNothing().when(hourlyStatsService).incrementInvalidRequests(customerId, timestamp); //mock stats service

        //build request WITHOUT customerID
        String requstBody = getPayloadString(customerId, false, tagId, true, userId, true, remoteIp, true, timestamp, true);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
        .post("/customer")
        .content(requstBody)
        .accept(MediaType.ALL)
        .contentType(MediaType.APPLICATION_JSON)
        .header("User-Agent", userAgent);

        //send request and get result
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        //assert that returned 400 BAD REQUEST
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        //assert returned error message
        assertEquals("invalid or missing customerID field", response.getContentAsString());

    }

    @Test
    public void testInvalidRequestNoTimestamp() throws Exception{        

        //mock dependencies
        Customer customer = getDummyCustomer(customerId, customerName, 1); //mock customer
        Mockito.when(customerService.getCustomerById(customerId)).thenReturn(customer); //mock customer service
        Mockito.when(ipblacklistService.isIpBlacklisted(getDummyIpBlacklist(remoteIp))).thenReturn(false); //mock IP blacklist service
        Mockito.when(uablacklistService.isUaBlacklisted(getDummyUaBlacklist(userAgent))).thenReturn(false); //mock UA blacklist service
        Mockito.doNothing().when(hourlyStatsService).incrementValidRequests(customerId, timestamp); //mock stats service
        Mockito.doNothing().when(hourlyStatsService).incrementInvalidRequests(customerId, timestamp); //mock stats service

        //build request WITHOUT timestamp
        String requstBody = getPayloadString(customerId, true, tagId, true, userId, true, remoteIp, true, timestamp, false);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
        .post("/customer")
        .content(requstBody)
        .accept(MediaType.ALL)
        .contentType(MediaType.APPLICATION_JSON)
        .header("User-Agent", userAgent);

        //send request and get result
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        //assert that returned 400 BAD REQUEST
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        //assert returned error message
        assertEquals("invalid or missing timestamp field", response.getContentAsString());

    }

    @Test
    public void testInvalidRequestNoTagId() throws Exception{        

        //mock dependencies
        Customer customer = getDummyCustomer(customerId, customerName, 1); //mock customer
        Mockito.when(customerService.getCustomerById(customerId)).thenReturn(customer); //mock customer service
        Mockito.when(ipblacklistService.isIpBlacklisted(getDummyIpBlacklist(remoteIp))).thenReturn(false); //mock IP blacklist service
        Mockito.when(uablacklistService.isUaBlacklisted(getDummyUaBlacklist(userAgent))).thenReturn(false); //mock UA blacklist service
        Mockito.doNothing().when(hourlyStatsService).incrementValidRequests(customerId, timestamp); //mock stats service
        Mockito.doNothing().when(hourlyStatsService).incrementInvalidRequests(customerId, timestamp); //mock stats service

        //build request WITHOUT tag id
        String requstBody = getPayloadString(customerId, true, tagId, false, userId, true, remoteIp, true, timestamp, true);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
        .post("/customer")
        .content(requstBody)
        .accept(MediaType.ALL)
        .contentType(MediaType.APPLICATION_JSON)
        .header("User-Agent", userAgent);

        //send request and get result
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        //assert that returned 400 BAD REQUEST
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        //assert returned error message
        assertEquals("invalid or missing tagID field", response.getContentAsString());

    }

    @Test
    public void testInvalidRequestNoUserId() throws Exception{        

        //mock dependencies
        Customer customer = getDummyCustomer(customerId, customerName, 1); //mock customer
        Mockito.when(customerService.getCustomerById(customerId)).thenReturn(customer); //mock customer service
        Mockito.when(ipblacklistService.isIpBlacklisted(getDummyIpBlacklist(remoteIp))).thenReturn(false); //mock IP blacklist service
        Mockito.when(uablacklistService.isUaBlacklisted(getDummyUaBlacklist(userAgent))).thenReturn(false); //mock UA blacklist service
        Mockito.doNothing().when(hourlyStatsService).incrementValidRequests(customerId, timestamp); //mock stats service
        Mockito.doNothing().when(hourlyStatsService).incrementInvalidRequests(customerId, timestamp); //mock stats service

        //build request WITHOUT user id
        String requstBody = getPayloadString(customerId, true, tagId, true, userId, false, remoteIp, true, timestamp, true);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
        .post("/customer")
        .content(requstBody)
        .accept(MediaType.ALL)
        .contentType(MediaType.APPLICATION_JSON)
        .header("User-Agent", userAgent);

        //send request and get result
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        //assert that returned 400 BAD REQUEST
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        //assert returned error message
        assertEquals("invalid or missing userID field", response.getContentAsString());

    }

    @Test
    public void testInvalidRequestNoRemoteip() throws Exception{        

        //mock dependencies
        Customer customer = getDummyCustomer(customerId, customerName, 1); //mock customer
        Mockito.when(customerService.getCustomerById(customerId)).thenReturn(customer); //mock customer service
        Mockito.when(ipblacklistService.isIpBlacklisted(getDummyIpBlacklist(remoteIp))).thenReturn(false); //mock IP blacklist service
        Mockito.when(uablacklistService.isUaBlacklisted(getDummyUaBlacklist(userAgent))).thenReturn(false); //mock UA blacklist service
        Mockito.doNothing().when(hourlyStatsService).incrementValidRequests(customerId, timestamp); //mock stats service
        Mockito.doNothing().when(hourlyStatsService).incrementInvalidRequests(customerId, timestamp); //mock stats service

        //build request without IP
        String requstBody = getPayloadString(customerId, true, tagId, true, userId, true, remoteIp, false, timestamp, true);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
        .post("/customer")
        .content(requstBody)
        .accept(MediaType.ALL)
        .contentType(MediaType.APPLICATION_JSON)
        .header("User-Agent", userAgent);

        //send request and get result
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        //assert that returned 400 BAD REQUEST
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        //assert returned error message
        assertEquals("invalid or missing remoteIP field", response.getContentAsString());

    }

    @Test
    public void testInvalidRequestCustomerNotFound() throws Exception{        

        //mock dependencies
        //mock customer service getCustomerById() return null for the request's customer id
        Mockito.when(customerService.getCustomerById(customerId)).thenReturn(null); //mock customer service
        Mockito.when(ipblacklistService.isIpBlacklisted(getDummyIpBlacklist(remoteIp))).thenReturn(false); //mock IP blacklist service
        Mockito.when(uablacklistService.isUaBlacklisted(getDummyUaBlacklist(userAgent))).thenReturn(false); //mock UA blacklist service
        Mockito.doNothing().when(hourlyStatsService).incrementValidRequests(customerId, timestamp); //mock stats service
        Mockito.doNothing().when(hourlyStatsService).incrementInvalidRequests(customerId, timestamp); //mock stats service

        //build request
        String requstBody = getPayloadString(customerId, true, tagId, true, userId, true, remoteIp, true, timestamp, true);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
        .post("/customer")
        .content(requstBody)
        .accept(MediaType.ALL)
        .contentType(MediaType.APPLICATION_JSON)
        .header("User-Agent", userAgent);

        //send request and get result
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        //assert that returned 400 BAD REQUEST
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        //assert returned error message
        assertEquals("customerID "+customerId+" not found", response.getContentAsString());

    }

    @Test
    public void testInvalidRequestCustomerNotActive() throws Exception{        

        //mock dependencies
        Customer customer = getDummyCustomer(customerId, customerName, 0); //mock customer NOT ACTIVE
        Mockito.when(customerService.getCustomerById(customerId)).thenReturn(customer); //mock customer service
        Mockito.when(ipblacklistService.isIpBlacklisted(getDummyIpBlacklist(remoteIp))).thenReturn(false); //mock IP blacklist service
        Mockito.when(uablacklistService.isUaBlacklisted(getDummyUaBlacklist(userAgent))).thenReturn(false); //mock UA blacklist service
        Mockito.doNothing().when(hourlyStatsService).incrementValidRequests(customerId, timestamp); //mock stats service
        Mockito.doNothing().when(hourlyStatsService).incrementInvalidRequests(customerId, timestamp); //mock stats service

        //build request
        String requstBody = getPayloadString(customerId, true, tagId, true, userId, true, remoteIp, true, timestamp, true);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
        .post("/customer")
        .content(requstBody)
        .accept(MediaType.ALL)
        .contentType(MediaType.APPLICATION_JSON)
        .header("User-Agent", userAgent);

        //send request and get result
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        //assert that returned 400 BAD REQUEST
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        //assert returned error message
        assertEquals("INACTIVE CUSTOMER, customerID: "+customerId+" , name: "+customerName, response.getContentAsString());

    }

    @Test
    public void testInvalidRequestIpBlacklisted() throws Exception{        

        //mock dependencies - ipblacklistService.isIpBlacklisted() returns true
        Customer customer = getDummyCustomer(customerId, customerName, 1); //mock customer
        Mockito.when(customerService.getCustomerById(customerId)).thenReturn(customer); //mock customer service
        Mockito.when(ipblacklistService.isIpBlacklisted(getDummyIpBlacklist(remoteIp))).thenReturn(true); //mock IP blacklist service
        Mockito.when(uablacklistService.isUaBlacklisted(getDummyUaBlacklist(userAgent))).thenReturn(false); //mock UA blacklist service
        Mockito.doNothing().when(hourlyStatsService).incrementValidRequests(customerId, timestamp); //mock stats service
        Mockito.doNothing().when(hourlyStatsService).incrementInvalidRequests(customerId, timestamp); //mock stats service


        //build request
        String requstBody = getPayloadString(customerId, true, tagId, true, userId, true, remoteIp, true, timestamp, true);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
        .post("/customer")
        .content(requstBody)
        .accept(MediaType.ALL)
        .contentType(MediaType.APPLICATION_JSON)
        .header("User-Agent", userAgent);

        //send request and get result
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        //assert that returned 400 BAD REQUEST
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        //assert returned error message
        assertEquals("IP address "+remoteIp+" is blacklisted", response.getContentAsString());

    }

    @Test
    public void testInvalidRequestUaBlacklisted() throws Exception{        

        //mock dependencies - uablacklistService.isUaBlacklisted() returns true
        Customer customer = getDummyCustomer(customerId, customerName, 1); //mock customer
        Mockito.when(customerService.getCustomerById(customerId)).thenReturn(customer); //mock customer service
        Mockito.when(ipblacklistService.isIpBlacklisted(getDummyIpBlacklist(remoteIp))).thenReturn(false); //mock IP blacklist service
        Mockito.when(uablacklistService.isUaBlacklisted(getDummyUaBlacklist(userAgent))).thenReturn(true); //mock UA blacklist service
        Mockito.doNothing().when(hourlyStatsService).incrementValidRequests(customerId, timestamp); //mock stats service
        Mockito.doNothing().when(hourlyStatsService).incrementInvalidRequests(customerId, timestamp); //mock stats service

        //build request
        String requstBody = getPayloadString(customerId, true, tagId, true, userId, true, remoteIp, true, timestamp, true);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
        .post("/customer")
        .content(requstBody)
        .accept(MediaType.ALL)
        .contentType(MediaType.APPLICATION_JSON)
        .header("User-Agent", userAgent);

        //send request and get result
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        //assert that returned 400 BAD REQUEST
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        //assert returned error message
        assertEquals("User Agent "+userAgent+" is blacklisted", response.getContentAsString());

    }

    //receives incoming request params and a flag indicating if to include them in the request payload or not
    private String getPayloadString(int customerId, boolean includeCustomerId, int tagId, boolean includeTagId, String userId, boolean includeUserId, String remoteIp, boolean includeremoteIp, long timestamp, boolean includeTimestamp) throws JSONException
    {
        JSONObject requestJson = new JSONObject();
        if (includeCustomerId) requestJson.put("customerID", customerId);
        if (includeTagId) requestJson.put("tagID", tagId);
        if (includeUserId) requestJson.put("userID", userId);
        if (includeremoteIp) requestJson.put("remoteIP", remoteIp);
        if (includeTimestamp) requestJson.put("timestamp", timestamp);
        return requestJson.toString();        
    }

    private Customer getDummyCustomer(int customerId, String customerName, int isActive){
        Customer customer = new Customer();
        customer.setId(customerId);
        customer.setName(customerName);
        customer.setIsActive(isActive);
        return customer;
    }

    private IpBlacklist getDummyIpBlacklist(String remoteIp) {
        IpBlacklist entry = new IpBlacklist();
        entry.setIp(remoteIp);
        return entry;
    }

    private UaBlacklist getDummyUaBlacklist(String userAgent) {
        UaBlacklist entry = new UaBlacklist();
        entry.setUa(userAgent);
        return entry;
    }
    
}
