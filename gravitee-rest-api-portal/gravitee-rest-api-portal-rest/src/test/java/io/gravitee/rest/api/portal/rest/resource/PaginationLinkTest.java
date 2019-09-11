/**
 * Copyright (C) 2015 The Gravitee team (http://gravitee.io)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.gravitee.rest.api.portal.rest.resource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.doReturn;

import java.net.URI;
import java.net.URISyntaxException;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.gravitee.rest.api.portal.rest.model.Links;

/**
 * @author Florent CHAMFROY (florent.chamfroy at graviteesource.com)
 * @author GraviteeSource Team
 */
@RunWith(MockitoJUnitRunner.class)
public class PaginationLinkTest {

    AbstractResource paginatedResourceForTest = new AbstractResource() {};
    
    @Mock
    UriInfo uriInfo;
    
    String path = "/links";
    
    @Before
    public void init() throws URISyntaxException {
            doReturn(new URI(path)).when(uriInfo).getAbsolutePath();
    }
    
    @Test
    public void testSpecificPageSpecificSize() throws URISyntaxException {
        Integer page = 3;
        Integer size = 15;
        Integer totalItems = 120;
        
        MultivaluedMap<String, String> queryParameters = new MultivaluedHashMap<String, String>();
        queryParameters.putSingle(AbstractResource.PAGE_QUERY_PARAM_NAME, page.toString());
        queryParameters.putSingle(AbstractResource.SIZE_QUERY_PARAM_NAME, size.toString());

        doReturn(new URI(path+"?page="+page+"&size="+size)).when(uriInfo).getRequestUri();
        doReturn(queryParameters).when(uriInfo).getQueryParameters();
        
        String expectedSelf = path+"?page=3&size=15";
        String expectedFirst= path+"?page=1&size=15";
        String expectedPrev = path+"?page=2&size=15";
        String expectedNext = path+"?page=4&size=15";
        String expectedLast = path+"?page=8&size=15";
        
        testGenericPaginatedLinks(page, size, totalItems, expectedSelf, expectedFirst, expectedPrev, expectedNext,
                expectedLast);
    }

    @Test
    public void testFirstPageSpecificSize() throws URISyntaxException {
        Integer page = 1;
        Integer size = 15;
        Integer totalItems = 120;
        
        MultivaluedMap<String, String> queryParameters = new MultivaluedHashMap<String, String>();
        queryParameters.putSingle(AbstractResource.PAGE_QUERY_PARAM_NAME, page.toString());
        queryParameters.putSingle(AbstractResource.SIZE_QUERY_PARAM_NAME, size.toString());

        doReturn(new URI(path+"?page="+page+"&size="+size)).when(uriInfo).getRequestUri();
        doReturn(queryParameters).when(uriInfo).getQueryParameters();
        
        String expectedSelf = path+"?page=1&size=15";
        String expectedFirst= path+"?page=1&size=15";
        String expectedPrev = null;
        String expectedNext = path+"?page=2&size=15";
        String expectedLast = path+"?page=8&size=15";
        
        testGenericPaginatedLinks(page, size, totalItems, expectedSelf, expectedFirst, expectedPrev, expectedNext,
                expectedLast);
    }
    
    @Test
    public void testLastPageSpecificSize() throws URISyntaxException {
        Integer page = 8;
        Integer size = 15;
        Integer totalItems = 120;
        
        MultivaluedMap<String, String> queryParameters = new MultivaluedHashMap<String, String>();
        queryParameters.putSingle(AbstractResource.PAGE_QUERY_PARAM_NAME, page.toString());
        queryParameters.putSingle(AbstractResource.SIZE_QUERY_PARAM_NAME, size.toString());

        doReturn(new URI(path+"?page="+page+"&size="+size)).when(uriInfo).getRequestUri();
        doReturn(queryParameters).when(uriInfo).getQueryParameters();
        
        String expectedSelf = path+"?page=8&size=15";
        String expectedFirst= path+"?page=1&size=15";
        String expectedPrev = path+"?page=7&size=15";
        String expectedNext = null;
        String expectedLast = path+"?page=8&size=15";
        
        testGenericPaginatedLinks(page, size, totalItems, expectedSelf, expectedFirst, expectedPrev, expectedNext,
                expectedLast);
    }
    
    @Test
    public void testSpecificPageDefaultSize() throws URISyntaxException {
        Integer page = 3;
        Integer size = 10;
        Integer totalItems = 120;
        
        MultivaluedMap<String, String> queryParameters = new MultivaluedHashMap<String, String>();
        queryParameters.putSingle(AbstractResource.PAGE_QUERY_PARAM_NAME, page.toString());

        doReturn(new URI(path+"?page="+page)).when(uriInfo).getRequestUri();
        doReturn(queryParameters).when(uriInfo).getQueryParameters();
        
        String expectedSelf = path+"?page=3";
        String expectedFirst= path+"?page=1";
        String expectedPrev = path+"?page=2";
        String expectedNext = path+"?page=4";
        String expectedLast = path+"?page=12";
        
        testGenericPaginatedLinks(page, size, totalItems, expectedSelf, expectedFirst, expectedPrev, expectedNext,
                expectedLast);
    }
    
    @Test
    public void testDefaultPageSpecificSize() throws URISyntaxException {
        Integer page = 1;
        Integer size = 15;
        Integer totalItems = 120;
        
        MultivaluedMap<String, String> queryParameters = new MultivaluedHashMap<String, String>();
        queryParameters.putSingle(AbstractResource.SIZE_QUERY_PARAM_NAME, size.toString());

        doReturn(new URI(path+"?size="+size)).when(uriInfo).getRequestUri();
        doReturn(queryParameters).when(uriInfo).getQueryParameters();
        
        String expectedSelf = path+"?size=15";
        String expectedFirst= path+"?page=1&size=15";
        String expectedPrev = null;
        String expectedNext = path+"?page=2&size=15";
        String expectedLast = path+"?page=8&size=15";
        
        testGenericPaginatedLinks(page, size, totalItems, expectedSelf, expectedFirst, expectedPrev, expectedNext,
                expectedLast);
    }
    
    @Test
    public void testDefaultParams() throws URISyntaxException {
        Integer page = 1;
        Integer size = 10;
        Integer totalItems = 120;
        
        MultivaluedMap<String, String> queryParameters = new MultivaluedHashMap<String, String>();

        doReturn(new URI(path)).when(uriInfo).getRequestUri();
        doReturn(queryParameters).when(uriInfo).getQueryParameters();
        
        String expectedSelf = path;
        String expectedFirst= path+"?page=1";
        String expectedPrev = null;
        String expectedNext = path+"?page=2";
        String expectedLast = path+"?page=12";
        
        testGenericPaginatedLinks(page, size, totalItems, expectedSelf, expectedFirst, expectedPrev, expectedNext,
                expectedLast);
    }
    
    @Test
    public void testDefaultParamsOnePagedResult() throws URISyntaxException {
        Integer page = 1;
        Integer size = 10;
        Integer totalItems = 3;
        
        doReturn(new URI(path)).when(uriInfo).getRequestUri();
        
        String expectedSelf = path;
        String expectedFirst= null;
        String expectedPrev = null;
        String expectedNext = null;
        String expectedLast = null;
        
        testGenericPaginatedLinks(page, size, totalItems, expectedSelf, expectedFirst, expectedPrev, expectedNext,
                expectedLast);
    }
    
    @Test
    public void testSpecificPageSpecificSizeOnePagedResult() throws URISyntaxException {
        Integer page = 1;
        Integer size = 15;
        Integer totalItems = 3;

        doReturn(new URI(path+"?page="+page+"&size="+size)).when(uriInfo).getRequestUri();
        
        String expectedSelf = path+"?page=1&size=15";
        String expectedFirst= null;
        String expectedPrev = null;
        String expectedNext = null;
        String expectedLast = null;
        
        testGenericPaginatedLinks(page, size, totalItems, expectedSelf, expectedFirst, expectedPrev, expectedNext,
                expectedLast);
    }
    
    @Test
    public void testDefaultPageSpecificSizeOnePagedResult() throws URISyntaxException {
        Integer page = 1;
        Integer size = 15;
        Integer totalItems = 3;
        
        doReturn(new URI(path+"?size="+size)).when(uriInfo).getRequestUri();
        
        String expectedSelf = path+"?size=15";
        String expectedFirst= null;
        String expectedPrev = null;
        String expectedNext = null;
        String expectedLast = null;
        
        testGenericPaginatedLinks(page, size, totalItems, expectedSelf, expectedFirst, expectedPrev, expectedNext,
                expectedLast);
    }
    
    @Test
    public void shouldHaveNoLinkWithNoElement() {
        Integer page = 1;
        Integer size = 10;
        Integer totalItems = 0;
        
        Links links = paginatedResourceForTest.computePaginatedLinks(uriInfo, page, size, totalItems);
        assertNull(links);
    }
    
    @Test
    public void shouldHaveNoLinkWithZeroPageNumber() {
        Integer page = 0;
        Integer size = 10;
        Integer totalItems = 100;
        
        Links links = paginatedResourceForTest.computePaginatedLinks(uriInfo, page, size, totalItems);
        assertNull(links);
    }
    
    @Test
    public void shouldHaveNoLinkWithTooBigPageNumber() {
        Integer page = 20;
        Integer size = 10;
        Integer totalItems = 100;
        
        Links links = paginatedResourceForTest.computePaginatedLinks(uriInfo, page, size, totalItems);
        assertNull(links);
    }
    
    private void testGenericPaginatedLinks(Integer page, Integer size, Integer totalItems, String expectedSelf,
            String expectedFirst, String expectedPrev, String expectedNext, String expectedLast) {
        Links links = paginatedResourceForTest.computePaginatedLinks(uriInfo, page, size, totalItems);
        assertEquals(expectedSelf, links.getSelf());
        assertEquals(expectedFirst, links.getFirst());
        assertEquals(expectedPrev, links.getPrev());
        assertEquals(expectedNext, links.getNext());
        assertEquals(expectedLast, links.getLast());
    }
    
    
    
}