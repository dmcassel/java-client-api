package com.marklogic.client.example.handle;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.jdom.Document;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import com.marklogic.client.DatabaseClient;
import com.marklogic.client.DatabaseClientFactory;
import com.marklogic.client.DatabaseClientFactory.Authentication;
import com.marklogic.client.DocumentIdentifier;
import com.marklogic.client.XMLDocumentManager;

/**
 * JDOMHandleExample illustrates writing and reading content as a JDOM structure
 * using the JDOMHandle example of a content handle extension.
 */
public class JDOMHandleExample {

	public static void main(String[] args) throws IOException, JDOMException {
		Properties props = loadProperties();

		// connection parameters for writer user
		String         host            = props.getProperty("example.host");
		int            port            = Integer.parseInt(props.getProperty("example.port"));
		String         writer_user     = props.getProperty("example.writer_user");
		String         writer_password = props.getProperty("example.writer_password");
		Authentication authType        = Authentication.valueOf(
				props.getProperty("example.authentication_type").toUpperCase()
				);

		run(host, port, writer_user, writer_password, authType);
	}

	public static void run(String host, int port, String user, String password, Authentication authType)
	throws JDOMException, IOException {
		System.out.println("example: "+JDOMHandleExample.class.getName());

		String filename = "flipper.xml";

		// connect the client
		DatabaseClient client = DatabaseClientFactory.connect(host, port, user, password, authType);

		// create a manager for documents of any format
		XMLDocumentManager docMgr = client.newXMLDocumentManager();

		// parse the example file into a JDOM structure
		InputStream docStream = JDOMHandleExample.class.getClassLoader().getResourceAsStream(
				"data"+File.separator+filename);
		if (docStream == null)
			throw new RuntimeException("Could not read document example");

		Document writeDocument = new SAXBuilder(false).build(docStream);

		// create an identifier for the document
		DocumentIdentifier docId = client.newDocId("/example/"+filename);

		// create a handle for the document
		JDOMHandle writeHandle = new JDOMHandle();
		writeHandle.set(writeDocument);

		// write the document
		docMgr.write(docId, writeHandle);

		// create a handle to receive the document content
		JDOMHandle readHandle = new JDOMHandle();

		// read the document content
		docMgr.read(docId, readHandle);

		// access the document content
		Document readDocument = readHandle.get();

		// ... do something with the document content ...

		String rootName = readDocument.getRootElement().getName();

		// delete the document
		docMgr.delete(docId);

		System.out.println("Wrote and read /example/"+filename+
				" content with the <"+rootName+"/> root element using JDOM");

		// release the client
		client.release();
	}

	// get the configuration for the example
	public static Properties loadProperties() throws IOException {
		String propsName = "Example.properties";
		InputStream propsStream = JDOMHandleExample.class.getClassLoader().getResourceAsStream(propsName);
		if (propsStream == null)
			throw new RuntimeException("Could not read example properties");

		Properties props = new Properties();
		props.load(propsStream);

		return props;
	}
}
