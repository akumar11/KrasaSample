# KrasaSample
Sample of Krasa JAXB Tool for demoing the issue with genratated javax.validation.constraints.Digits annotation.

===============================================
XSD defination (travel.xsd)
==============================================
        < xs:simpleType name="max11decimalvalue" >
 
		< xs:restriction base="xs:decimal">
			< xs:fractionDigits value="10">
			< /xs:fractionDigits>
			< xs:totalDigits value="11">< /xs:totalDigits>
		< /xs:restriction>
	< /xs:simpleType>
===========================================================================
javax.validation.constraints.Digits in JAXB Genrated bean (as per above xsd)
===========================================================================
 @Digits(integer = 1, fraction = 10)
	
	
===========================================================================
Data Validations
===========================================================================
	a) For XSD and XML Data validation Following Data Combination are valid/invalid :
	
	<trv:distance>1.1234567890</trv:distance> (VALID)
	
	<trv:distance>9.1234567890</trv:distance> (VALID)
	
	<trv:distance>9.1234567890</trv:distance> (VALID)
    
	b) For JAXB Generated Bean validation Following Data Combination are valid/invalid
     
	1.1234567890 (VALID)
	9.1234567890 (VALID)
	11.123456789 (INVALID)
	
For More Information , refer Junit test file "XsdObjectValidateTest"

	
