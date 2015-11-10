# KrasaSample
Sample for Krasa JAXB validation

Issue is with following XSD defination (travel.xsd)

<xs:simpleType name="max11decimalvalue">
		<xs:restriction base="xs:decimal">
			<xs:fractionDigits value="10">
			</xs:fractionDigits>
			<xs:totalDigits value="11"></xs:totalDigits>
		</xs:restriction>
	</xs:simpleType>
	
	For XML Data validation Following Data Combination are valid :
	
	<trv:distance>1.1234567890</trv:distance> (VALID)
	
