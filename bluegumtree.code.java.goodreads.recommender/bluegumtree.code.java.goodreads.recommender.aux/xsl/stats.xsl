<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:xs="http://www.w3.org/2001/XMLSchema"
    exclude-result-prefixes="xs"
    version="2.0">
    
    
    <xsl:template match="books">
        
        <h2>Summary</h2>    	
        <table border="1" cellspacing="1" cellpadding="2">
            <tr>
                <!-- th rowspan="2">Goodreads Book ID</th-->
                <!-- th colspan="4">Training</th -->
                <th colspan="4">Testing</th>
                <th colspan="4">Recommendations</th>
                <th colspan="4">Accuracy</th>
            </tr>
            <tr>        	        		
                <!-- Training -->
                <!-- th>Popular</th>
                    <th>Mediocre</th>
                    <th>Unpopular</th>
                    <th>Total Books</th -->
                <!-- Testing -->
                <th>Popular</th>
                <th>Mediocre</th>
                <th>Unpopular</th>
                <th>Total Books</th>
                <!-- Recommendations -->
                <th>Popular</th>
                <th>Mediocre</th>
                <th>Unpopular</th>
                <th>Total Books</th>
                <!-- Accuracy -->
                <th>Popular</th>
                <th>Mediocre</th>
                <th>Unpopular</th>
                <th>Total Books</th>
            </tr>
            <xsl:apply-templates select="." mode="data" />
        </table>
    </xsl:template>
    
    <xsl:template match="books" mode="data">
        <tr>
            <!-- td><xsl:value-of select="@id" /></td-->    		
            <!-- Training -->
            <!-- td><xsl:value-of select="rating"/></td>
                <td><xsl:value-of select="rating"/></td>
                <td><xsl:value-of select="rating"/></td>
                <td><xsl:value-of select="rating"/></td -->
            
            <!-- Testing -->
            <xsl:variable name="tested" select="//books/book" />
            <xsl:variable name="testedPopular" select="//books/book[@rating &gt; 3]" />
            <xsl:variable name="testedMediocre" select="//books/book[@rating = 3]" />
            <xsl:variable name="testedUnpopular" select="//books/book[@rating &lt; 3]" />
            
            <td><xsl:value-of select="count($testedPopular)"/></td>
            <td><xsl:value-of select="count($testedMediocre)"/></td>
            <td><xsl:value-of select="count($testedUnpopular)"/></td>
            <td><xsl:value-of select="count($tested)"/></td>
            
            <!-- Recommendations -->
            <xsl:variable name="recommended" select="//recommendation[. &gt; 0]" />
            <xsl:variable name="recommendedPopular" select="$recommended[@book = $testedPopular/@id]" />
            <xsl:variable name="recommendedMediocre" select="$recommended[@book = $testedMediocre/@id]" />
            <xsl:variable name="recommendedUnpopular" select="$recommended[@book = $testedUnpopular/@id]" />
            
            <td><xsl:value-of select="count($recommendedPopular)"/></td>
            <td><xsl:value-of select="count($recommendedMediocre)"/></td>
            <td><xsl:value-of select="count($recommendedUnpopular)"/></td>
            <td><xsl:value-of select="count($recommended)"/></td>
            
            <!-- Evaluation -->
            <xsl:variable name="accuracyPopular" select="sum(sum(count($recommendedPopular) div count($testedPopular)) * 100)"/>
            <xsl:variable name="accuracyMediocre" select="sum(100 - (sum(count($recommendedMediocre) div count($testedMediocre)) * 100))"/>
            <xsl:variable name="accuracyUnpopular" select="sum(100 - (sum(count($recommendedUnpopular) div count($testedUnpopular)) * 100))"/>
            <xsl:variable name="accuracyAll" select="sum(count(//recommendation[@accurate = 'true']) div count($tested)) * 100"  />
            
            <td><xsl:value-of select="format-number($accuracyPopular, '##0.00')" /><xsl:value-of select="'%'" /></td>
            <td><xsl:value-of select="format-number($accuracyMediocre, '##0.00')" /><xsl:value-of select="'%'" /></td>
            <td><xsl:value-of select="format-number($accuracyUnpopular, '##0.00')" /><xsl:value-of select="'%'" /></td>
            <td><xsl:value-of select="format-number($accuracyAll, '##0.00')" /><xsl:value-of select="'%'" /></td>
        </tr>
    </xsl:template>
    
    
</xsl:stylesheet>