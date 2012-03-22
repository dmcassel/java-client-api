package com.marklogic.client.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.marklogic.client.AbstractDocumentManager.Metadata;
import com.marklogic.client.DatabaseClientFactory.Authentication;
import com.marklogic.client.config.search.QueryDefinition;
import com.marklogic.client.config.search.SearchOptions;

public interface RESTServices {
	public void connect(String host, int port, String user, String password, Authentication type);
	public void release();

	public void deleteDocument(String uri, String transactionId, Set<Metadata> categories);
	public <T> T getDocument(String uri, String transactionId, Set<Metadata> categories, Map<String,String> extraParams, String mimetype, Class<T> as);
	public Object[] getDocument(String uri, String transactionId, Set<Metadata> categories, Map<String,String> extraParams, String[] mimetypes, Class[] as);
	public Map<String,List<String>> head(String uri, String transactionId);
	public void putDocument(String uri, String transactionId, Set<Metadata> categories, Map<String,String> extraParams, String mimetype, Object value);
	public void putDocument(String uri, String transactionId, Set<Metadata> categories, Map<String,String> extraParams, String[] mimetypes, Object[] values);

    public <T> T search(Class <T> as, QueryDefinition queryDef, long start, String transactionId);

	public String openTransaction();
	public void commitTransaction(String transactionId);
	public void rollbackTransaction(String transactionId);

	public SearchOptions get(String searchOptionsName);
	public void put(String searchOptionsName, SearchOptions options);
	public void delete(String searchOptionsName);

	// namespaces, etc.
	public <T> T getValue(String type, String key, String mimetype, Class<T> as);
	public <T> T getValues(String type, String mimetype, Class<T> as);
	public void postValue(String type, String key, String mimetype, Object value);
	public void putValue(String type, String key, String mimetype, Object value);
	public void deleteValue(String type, String key);
	public void deleteValues(String type);
}
