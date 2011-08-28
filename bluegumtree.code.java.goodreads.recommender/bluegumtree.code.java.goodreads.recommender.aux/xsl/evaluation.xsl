<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:xs="http://www.w3.org/2001/XMLSchema"
    exclude-result-prefixes="xs"
    version="2.0"
    xml:base="xsl">
 
	<xsl:include href="stats.xsl"/>
	
	<xsl:param name="saveLocation" select="'/home/sheila/goodreads/db/recommendations/out/'" as="xs:string" />
   	
    <xsl:output 
        name="review"
        encoding="UTF-8"
        media-type="text/html"
        method="html"
        indent="yes"
        omit-xml-declaration="yes"
    />
	
    <xsl:output name="console" encoding="UTF-8" media-type="text" method="text" omit-xml-declaration="yes" />
    
    <xsl:variable name="UserId" select="/user/@id" />
	<xsl:variable name="Factor" select="/user/recommendations/@factor" />
	<xsl:variable name="Measure" select="/user/recommendations/@measure" />
	
    
    <xsl:template match="/">            
        
        <xsl:result-document href="{$saveLocation}{$UserId}/results/{$Factor}_{$Measure}.html">
            <html> 
            	<head>
            		<title><xsl:value-of select="//recommendations/@factor" /> - <xsl:value-of select="//recommendations/@measure" /></title>
            	</head>
                <body><xsl:apply-templates select="user" /></body>        
            </html>
        </xsl:result-document>
    	
    </xsl:template>
    
    <xsl:template match="user">
        <h1><xsl:value-of select="//recommendations/@factor" /><br/><xsl:value-of select="//recommendations/@measure" /></h1>
    	<p>User ID: <xsl:value-of select="$UserId" /></p>
    	
        <xsl:apply-templates select="*" />   
    </xsl:template>
    
       
	<xsl:template match="recommendations">
		
		<h2>Books</h2>
		<table border="1" cellspacing="1" cellpadding="1">
			<tr>				
				<th>Goodreads ID</th>
				<th>Title</th>
				<th>Recommended</th>
				<th>Grade</th>
				<th>Rating</th>
				<th>Originally Published</th>
				<th>Edition Published</th>
				<th>Total Pages</th>
				<th>ISBN-10</th>
				<th>ISBN-13</th>
			</tr>			
			<tr>
				<xsl:apply-templates select="recommendation">
					<xsl:sort select="//book[@id = current()/@book]/@rating" order="descending" data-type="number" />
					<xsl:sort select="if (. &gt; 0) then 1 else 0" order="descending" data-type="number" />
					<xsl:sort select="." order="descending" data-type="number" />					
				</xsl:apply-templates>
			</tr>			
		</table>
			
	</xsl:template>
	
	<xsl:template match="recommendation">
		<xsl:variable name="book" select="//book[@id = current()/@book]" as="element()?" />
		
		<tr>			
			<td><xsl:value-of select="$book/@id"/></td>
			<td><xsl:value-of select="$book/title"/></td>
			<td><xsl:value-of select="if (. &gt; 0) then 'Y' else 'N'"/></td>
			<td><xsl:value-of select="." /></td>
			<td><xsl:value-of select="$book/@rating" /></td>
			<td><xsl:value-of select="$book/published[@original]/year"/></td>
			<td><xsl:value-of select="$book/published[not(@original)]/year"/></td>
			<td><xsl:value-of select="$book/pages"/></td>
			<td><xsl:value-of select="$book/isbn[@version = '10']"/></td>
			<td><xsl:value-of select="$book/isbn[@version = '13']"/></td>
		</tr>
	</xsl:template>
    
    
</xsl:stylesheet>
