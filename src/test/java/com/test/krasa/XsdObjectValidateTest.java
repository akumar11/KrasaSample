package com.test.krasa.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.math.BigDecimal;
import java.util.Set;
import java.util.logging.Logger;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.junit.Test;

import com.travel.srv.domain.travel.v1_0.TravelInfoData;
import com.travel.srv.domain.travel.v1_0.TravelMode;


/**
 * @author Arun.Kumar
 *
 */
public class XsdObjectValidateTest {

	private static final Logger logger = Logger
			.getLogger(XsdObjectValidateTest.class.getName());

	/**
	 * XSD And Test XML Locations
	 */
	String XSD_PATH = "src\\main\\resources\\xsd\\travel.xsd";
	String XML_PATH = "src\\test\\resources\\xml\\travelXMLSample.xml";

	BigDecimal DISTANCE_1 = new BigDecimal("1.1234567890");
	BigDecimal DISTANCE_2 = new BigDecimal("9.1234567890");
	BigDecimal DISTANCE_3 = new BigDecimal("11.123456789");

	/**
	 * This test is validating XML file with XSD file. Output of this will be
	 * true during parsing of XML file with XSD template file.
	 * 
	 * */

	@Test
	public void testXmlFile() {
		logger.info("Runnig XSD validation with XML file ......");
		try {
			assertTrue(validateXsdXmlFile(XSD_PATH, XML_PATH));
			logger.info("XSD Validation with XML file STATUS  = PASSED ");
		} catch (AssertionError e) {
			logger.info("XSD Validation STATUS  = FAILED ");
			throw e;
		}
	}

	/***
	 * This test is validating XSD object with bean validator which is generated
	 * by Krasa JAX-B tool with XJsr303Annotations. This tool added validation
	 * in bean validator with annotations.
	 * 
	 */

	@Test
	public void testXsdObject() {
		logger.info("Runnig XSD validation with Bean Validator ......");
		TravelInfoData travelInfoData1 = getTravelInfoDataCollection(
				DISTANCE_1, TravelMode.ROAD, "Alsaka");
		TravelInfoData travelInfoData2 = getTravelInfoDataCollection(
				DISTANCE_2, TravelMode.AIR, "Miami");
		TravelInfoData travelInfoData3 = getTravelInfoDataCollection(
				DISTANCE_3, TravelMode.ROAD, "London");
		try {
			assertEquals(Boolean.TRUE, validateBean(travelInfoData1).isEmpty());
			assertEquals(Boolean.TRUE, validateBean(travelInfoData2).isEmpty());

			// Validation Failed for DISTANCE_3 (11.123456789) which is passed
			// in
			// XML test case
			assertEquals(Boolean.FALSE, validateBean(travelInfoData3).isEmpty());
			logger.info("XSD Bean Validation STATUS  = PASSED ");
		} catch (AssertionError e) {
			logger.info("Bean Validation STATUS  = FAILED ");
			throw e;
		}

	}

	private Set<ConstraintViolation<TravelInfoData>> validateBean(
			TravelInfoData entity) {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		javax.validation.Validator validator = factory.getValidator();
		Set<ConstraintViolation<TravelInfoData>> validationSet = validator
				.validate(entity);
		return validationSet;
	}

	private TravelInfoData getTravelInfoDataCollection(BigDecimal distance,
			TravelMode travelMode, String destination) {
		TravelInfoData travelData = new TravelInfoData();
		travelData.setDistance(distance);
		travelData.setTravelMode(travelMode);
		travelData.setDestination(destination);
		return travelData;
	}

	private boolean validateXsdXmlFile(String xsdPath, String xmlPath) {
		try {
			SchemaFactory factory = SchemaFactory
					.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			Schema schema = factory.newSchema(new File(xsdPath));
			javax.xml.validation.Validator validator = schema.newValidator();
			validator.validate(new StreamSource(new File(xmlPath)));
		} catch (Exception e) {
			return false;
		}
		return true;
	}
}
