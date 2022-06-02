package com.kanz.newslettersubscriptionengine.newslettersubscription.testUtil;

import com.kanz.newslettersubscriptionengine.common.dto.BaseDto;
import com.kanz.newslettersubscriptionengine.common.dto.ResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;

import static com.kanz.newslettersubscriptionengine.common.util.EmptyUtil.isNotNull;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;


@Component
public class TestMockMvc {
    protected final Logger logger = LoggerFactory.getLogger(TestMockMvc.class);

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON
            .getSubtype(), Charset.forName("utf8"));

    private MockMvc mockMvc;

    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {
        this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream().filter(hmc -> hmc instanceof
                MappingJackson2HttpMessageConverter).findAny().orElse(null);
        assertNotNull("the JSON message converter must not be null", this.mappingJackson2HttpMessageConverter);
    }


    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    public String jsonify(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }

    public <R extends ResponseDto, T extends BaseDto> R callUrl(String url, HttpMethod method, T request,
                                                                ResultMatcher matcher, Class<R> clazz,
                                                                MultiValueMap<String, String> params, Object...
                                                                        uriVars) throws Exception {
        MockHttpServletRequestBuilder request1 = request(method, url, uriVars);
        if(isNotNull(params))
            request1.params(params);
        MvcResult mvcResult = getMockMvc().perform(request1.content(jsonify
                (request)).contentType(getContentType())).andExpect(matcher).andReturn();
        logger.info("resultActions = {}", mvcResult.getResponse().getContentAsString());
        R responseDto = TestUtility.readJson(mvcResult, clazz);
        logger.info("Response dto = {}", responseDto);
        return responseDto;
    }

    public MockMvc getMockMvc() {
        return mockMvc;
    }

    public MediaType getContentType() {
        return contentType;
    }
}
