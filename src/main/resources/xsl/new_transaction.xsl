<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0">
	<xsl:output method="html" include-content-type="no" doctype-system="about:legacy-compat" encoding="UTF-8" indent="yes"/>
    <xsl:include href="/xsl/layout.xsl"/>
	<xsl:template match="page" mode="head">
		<title>
			<xsl:text>Issuer payment processor - Route a transaction</xsl:text>
		</title>
	</xsl:template>
	<xsl:template match="page" mode="body">
	    <h1>
	      <xsl:text>Route a transaction</xsl:text>
	    </h1>
		<form action="/transaction/route" method="post">
	      <fieldset>
	        <label>
	          <a href="https://www.admfactory.com/iso8583-flows-data-elements-meaning-and-values/#field2" target="_blank">Field 2</a><xsl:text> - Primary Account Number (PAN)</xsl:text><span style="color: red"> *</span>
	        </label>
	        <input name="2" type="text" size="50" minlength="12" maxlength="19" required="" placeholder="Numeric - 12 to 19 digits"/>
	        <label>
	          <a href="https://www.admfactory.com/iso8583-flows-data-elements-meaning-and-values/#field3" target="_blank">Field 3</a><xsl:text> - Processing code</xsl:text><span style="color: red"> *</span>
	        </label>
	        <input name="3" type="text" size="50" minlength="6" maxlength="6" required="" placeholder="Numeric - 6 digits" value="506040"/>
			<label>
	          <a href="https://www.admfactory.com/iso8583-flows-data-elements-meaning-and-values/#field4" target="_blank">Field 4</a><xsl:text> - Amount, transaction</xsl:text><span style="color: red"> *</span>
	        </label>
	        <input name="4" type="text" size="50" minlength="12" maxlength="12" required="" placeholder="Numeric - 12 digits" value="000001200000"/>
	        <label>
	          <a href="https://www.admfactory.com/iso8583-flows-data-elements-meaning-and-values/#field7" target="_blank">Field 7</a><xsl:text> - Transmission date and time (MMDDhhmmss)</xsl:text><span style="color: red"> *</span>
	        </label>
	        <input name="7" type="text" size="50" minlength="10" maxlength="10" required="" placeholder="Numeric - 10 digits" value="0725155030"/>
	        <label>
	          <a href="https://www.admfactory.com/iso8583-flows-data-elements-meaning-and-values/#field11" target="_blank">Field 11</a><xsl:text> - System trace audit number (STAN)</xsl:text><span style="color: red"> *</span>
	        </label>
	        <input name="11" type="text" size="50" minlength="6" maxlength="6" required="" placeholder="Numeric - 6 digits" value="000001"/>
	        <label>
	          <a href="https://www.admfactory.com/iso8583-flows-data-elements-meaning-and-values/#field12" target="_blank">Field 12</a><xsl:text> - Time, local transaction (hhmmss)</xsl:text><span style="color: red"> *</span>
	        </label>
	        <input name="12" type="text" size="50" minlength="6" maxlength="6" required="" placeholder="Numeric - 6 digits" value="155030"/>
	        <label>
	          <a href="https://www.admfactory.com/iso8583-flows-data-elements-meaning-and-values/#field13" target="_blank">Field 13</a><xsl:text> - Date, local transaction (MMDD)</xsl:text><span style="color: red"> *</span>
	        </label>
	        <input name="13" type="text" size="50" minlength="4" maxlength="4" required="" placeholder="Numeric - 4 digits" value="0725"/>
	        <label>
	          <a href="https://www.admfactory.com/iso8583-flows-data-elements-meaning-and-values/#field22" target="_blank">Field 22</a><xsl:text> - Point of service entry mode</xsl:text><span style="color: red"> *</span>
	        </label>
	        <input name="22" type="text" size="50" minlength="3" maxlength="3" required="" placeholder="Numeric - 3 digits" value="010"/>
	        <label>
	          <a href="https://www.admfactory.com/iso8583-flows-data-elements-meaning-and-values/#field37" target="_blank">Field 37</a><xsl:text> - Retrieval reference number</xsl:text><span style="color: red"> *</span>
	        </label>
	        <input name="37" type="text" size="50" minlength="12" maxlength="12" required="" placeholder="Alphanumeric - 12 digits - must be unique" />
	        <label>
	          <a href="https://www.admfactory.com/iso8583-flows-data-elements-meaning-and-values/#field41" target="_blank">Field 41</a><xsl:text> - Card acceptor terminal identification</xsl:text><span style="color: red"> *</span>
	        </label>
	        <input name="41" type="text" size="50" minlength="8" maxlength="8" required="" placeholder="Alphanumeric and special characters - 8 digits" value="1@surati"/>
	        <label>
	          <a href="https://www.admfactory.com/iso8583-flows-data-elements-meaning-and-values/#field42" target="_blank">Field 42</a><xsl:text> - Card acceptor identification code</xsl:text><span style="color: red"> *</span>
	        </label>
	        <input name="42" type="text" size="50" minlength="15" maxlength="15" required="" placeholder="Alphanumeric and special characters - 15 digits" value="surati@market07"/>
	        <label>
	          <a href="https://www.admfactory.com/iso8583-flows-data-elements-meaning-and-values/#field43" target="_blank">Field 43</a><xsl:text> - Card acceptor name/location</xsl:text><span style="color: red"> *</span>
	        </label>
	        <input name="43" type="text" size="50" minlength="40" maxlength="40" required="" placeholder="Alphanumeric and special characters - 40 digits" value="01BP14895YAMOUSSOUKRO01YAKROMOROFE02RLCI"/>
	        <label>
	          <a href="https://www.admfactory.com/iso8583-flows-data-elements-meaning-and-values/#field49" target="_blank">Field 49</a><xsl:text> - Currency Code, Transaction</xsl:text><span style="color: red"> *</span>
	        </label>
	        <input name="49" type="text" size="50" minlength="3" maxlength="3" required="" placeholder="Alpha or numeric - 3 digits" value="XOF"/>
	        <label>
	          <xsl:text>Field 73 - Date, action (YYMMDD)</xsl:text><span style="color: red"> *</span>
	        </label>
	        <input name="73" type="text" size="50" minlength="6" maxlength="6" required="" placeholder="Numeric - 6 digits" value="200725"/>
	        <label>
	          <xsl:text>Field 104 - Transaction description</xsl:text><span style="color: red"> *</span>
	        </label>
	        <input name="104" type="text" size="50" minlength="1" maxlength="100" required="" placeholder="Alphanumeric and special characters - 1 to 100 digits" value="TEST PAYMENT ON CREDIT CARD FOR SURATI"/>
	        
	        <div style="display: inline-block">
	        	<button type="submit">
		          <xsl:text>Route to Surati</xsl:text>
		        </button>
		        <button type="button" onclick="location.href='/'">
		          <xsl:text>Cancel</xsl:text>
		        </button>
	        </div>
	      </fieldset>
	    </form>
	</xsl:template>
</xsl:stylesheet>
