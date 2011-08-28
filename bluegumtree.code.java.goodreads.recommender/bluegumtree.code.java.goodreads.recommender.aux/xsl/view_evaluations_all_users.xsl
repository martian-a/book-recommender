<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:xs="http://www.w3.org/2001/XMLSchema"
    xmlns:atom="http://www.w3.org/2005/Atom"
    xmlns:dc="http://purl.org/dc/terms"
    exclude-result-prefixes="xs"
    version="2.0"
    xml:base="xsl">

    <xsl:param name="saveLocation" select="''" as="xs:string" />

    <xsl:output 
        name="review"
        encoding="UTF-8"
        media-type="text/xml"
        method="xml"
        indent="yes"
        omit-xml-declaration="yes"      
    />
    
    <xsl:template match="/">        
        
        <!-- Build filename -->
        <xsl:variable name="filename" select="$saveLocation" />          
                   
        <xsl:result-document href="{$filename}" format="review" validation="strip">
            <html>
                <head>
                    <title>Factor Comparison</title>
                    <style type="text/css">
                        body, th, td {
                            font-size: 10px;                          
                        }
                        table {
                            width: 100%;
                            border-collapse: collapse;    
                            margin-bottom: 1em;
                        }
                        td, th {
                            border: 1px solid #CFCFCF;  
                            text-align: center;
                            padding: .1em;
                        }
                        td.recommended.true {
                            background-color: green;
                        }
                        td.accurate.true {
                            background-color: blue;
                        }
                    </style>
                </head>
                <body>                    
                    <h1>Factor Comparison</h1>
                    <xsl:apply-templates />
                </body>
            </html>            
        </xsl:result-document>
        
    </xsl:template>                
       
    <xsl:template match="users">
        
        <xsl:for-each-group select="//recommendations" group-by="@factor">
            <xsl:sort select="@factor" data-type="text" order="ascending" />
            
            <table border="0" cellspacing="0" cellpadding="0">
                <thead>
        
                    <tr>
                        <!-- Per User -->
                        <th rowspan="4">Goodreads ID</th>
                        <th rowspan="4">Total Books In Test Set</th>
                        <th rowspan="4">% Test Set Enjoyed</th>
                        <!-- Per Factor -->
                        <xsl:choose>
                            <xsl:when test="current-grouping-key() = 'ALL'">                            
                                <th colspan="10"><xsl:value-of select="@factor" /></th>    
                            </xsl:when>
                            <xsl:otherwise>
                                <th colspan="40"><xsl:value-of select="@factor" /></th>    
                            </xsl:otherwise>
                        </xsl:choose>
                    </tr>
                    <!-- Per Measure -->
                    <tr>
                        <xsl:choose>
                            <xsl:when test="current-grouping-key() = 'ALL'">
                                <xsl:for-each-group select="current-group()" group-by="@measure">
                                    <xsl:sort select="@measure" data-type="text" order="ascending" />
                                    
                                    <th colspan="10"><xsl:value-of select="@measure" /></th>
                                </xsl:for-each-group>        
                            </xsl:when>
                            <xsl:otherwise>
                                <xsl:for-each-group select="current-group()" group-by="@measure">
                                    <xsl:sort select="@factor" data-type="text" order="ascending" />
                                    <xsl:sort select="@measure" data-type="text" order="ascending" />
                                    
                                    <th colspan="10"><xsl:value-of select="@measure" /></th>
                                </xsl:for-each-group>                     
                            </xsl:otherwise>
                        </xsl:choose>
                    </tr>
                    <tr>                                       
                        <xsl:for-each-group select="current-group()" group-by="@measure">
                            <xsl:sort select="@factor" data-type="text" order="ascending" />
                            <xsl:sort select="@measure" data-type="text" order="ascending" />
                        
                            <th colspan="2">Accurately Recommended</th>
                            <th colspan="2">Popular Books Recommended (True Positive)</th>
                            <th colspan="2">Popular Books Not Recommended (False Negative)</th>
                            <th colspan="2">Non-Popular Books Recommended (False Positive)</th>
                            <th colspan="2">Non-Popular Books Not Recommended (True Negative)</th>
                       </xsl:for-each-group>                    
                    </tr>
                    <tr>                    
                        <xsl:for-each-group select="current-group()" group-by="@measure">
                            <xsl:sort select="@factor" data-type="text" order="ascending" />
                            <xsl:sort select="@measure" data-type="text" order="ascending" />
                            
                            <th>#</th>
                            <th>%</th>
                            <th>#</th>
                            <th>%</th>
                            <th>#</th>
                            <th>%</th>
                            <th>#</th>
                            <th>%</th>
                            <th>#</th>
                            <th>%</th>
                        </xsl:for-each-group>
                    </tr>
                </thead>
                <tbody>
                    <xsl:for-each select="//user">
                        <xsl:variable name="books" select="books/book" />
                        <xsl:variable name="popularBooks" select="$books[@rating &gt; 3]" />
                        <xsl:variable name="notPopularBooks" select="$books[not(@id = $popularBooks/@id)]" />
                        <xsl:variable name="percOfSetThatArePopular" select="if (count($popularBooks) &gt; 0) then sum(sum(count($popularBooks) div count($books)) * 100) else 0" />
                        
                        <tr>
                            <td><xsl:value-of select="@id" /></td>                        
                            <td><xsl:value-of select="count($books)" /></td>
                            <td><xsl:value-of select="format-number($percOfSetThatArePopular, '##0.00')" /></td>                                                            
                                
                               
                                <xsl:for-each-group select="current-group()" group-by="@measure">                                    
                                    
                                    <xsl:variable name="recommendedBooks" select="$books[@id = current-group()/recommendation[. &gt; 0]/@book]" />
                                    <xsl:variable name="notRecommendedBooks" select="$books[not(@id = $recommendedBooks/@id)]" />
                                    <xsl:variable name="popularBooksRecommended" select="$popularBooks[@id = $recommendedBooks/@id]"></xsl:variable>
                                    <xsl:variable name="popularBooksNotRecommended" select="$popularBooks[@id = $notRecommendedBooks/@id]" />
                                    <xsl:variable name="notPopularBooksRecommended" select="$notPopularBooks[@id = $recommendedBooks/@id]"></xsl:variable>
                                    <xsl:variable name="notPopularBooksNotRecommended" select="$notPopularBooks[@id = $notRecommendedBooks/@id]"></xsl:variable>
                                                                    
                                    <xsl:variable name="percPopularBooksRecommended" select="if (count($popularBooksRecommended) &gt; 0) then sum(sum(count($popularBooksRecommended) div count($popularBooks)) * 100) else 0" />
                                    <xsl:variable name="percPopularBooksNotRecommended" select="if (count($popularBooksNotRecommended) &gt; 0) then sum(sum(count($popularBooksNotRecommended) div count($popularBooks)) * 100) else 0" />
                                    <xsl:variable name="percNotPopularBooksRecommended" select="if (count($notPopularBooksRecommended) &gt; 0) then sum(sum(count($notPopularBooksRecommended) div count($notPopularBooks)) * 100) else 0" />
                                    <xsl:variable name="percNotPopularBooksNotRecommended" select="if (count($notPopularBooksRecommended) &gt; 0) then sum(sum(count($notPopularBooksNotRecommended) div count($notPopularBooks)) * 100) else 0" />
                                    
                                    <xsl:variable name="accurate" select="$books[@id = $popularBooksRecommended/@id or @id = $notPopularBooksNotRecommended/@id]" />
                                    <xsl:variable name="percAccurate" select="sum(count($accurate) div count($books)) * 100" />
                                    
                                    
                                    <td><xsl:value-of select="count($accurate)" /></td>
                                    <td><xsl:value-of select="format-number($percAccurate, '##0.00')" /></td>
                                    <td><xsl:value-of select="count($popularBooksRecommended)" /></td>
                                    <td><xsl:value-of select="format-number($percPopularBooksRecommended, '##0.00')"/></td>
                                    <td><xsl:value-of select="count($popularBooksNotRecommended)" /></td>
                                    <td><xsl:value-of select="format-number($percPopularBooksNotRecommended, '##0.00')" /></td>
                                    <td><xsl:value-of select="count($notPopularBooksRecommended)" /></td>
                                    <td><xsl:value-of select="format-number($percNotPopularBooksRecommended, '##0.00')" /></td>
                                    <td><xsl:value-of select="count($notPopularBooksNotRecommended)" /></td>
                                    <td><xsl:value-of select="format-number($percNotPopularBooksNotRecommended, '##0.00')" /></td>
                                </xsl:for-each-group>
                            
                        </tr>
                    </xsl:for-each>
                </tbody>
            </table>
        </xsl:for-each-group>
    </xsl:template>
    
    <xsl:template match="*" mode="merge">
        <xsl:element name="{name()}">
            <xsl:copy-of select="@*" />
            <xsl:apply-templates mode="merge" />
        </xsl:element>
    </xsl:template>
    
    <xsl:template match="text()" mode="merge">
        <xsl:value-of select="normalize-space(.)" />  
    </xsl:template>
    
</xsl:stylesheet>