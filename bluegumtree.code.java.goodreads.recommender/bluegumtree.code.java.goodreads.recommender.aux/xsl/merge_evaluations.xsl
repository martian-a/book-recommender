<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:xs="http://www.w3.org/2001/XMLSchema"
    xmlns:atom="http://www.w3.org/2005/Atom"
    xmlns:dc="http://purl.org/dc/terms"
    exclude-result-prefixes="xs"
    version="2.0"
    xml:base="xsl">

    <xsl:param name="saveLocation" select="''" as="xs:string" />
    <xsl:param name="dataLocation" select="''" as="xs:string" />
    
    <xsl:variable name="originalPublicationYearMean" select="document(concat($dataLocation, 'original_publication_year_MEAN.xml'))" />
    <xsl:variable name="originalPublicationYearMode" select="document(concat($dataLocation, 'original_publication_year_MODE.xml'))" />
    <xsl:variable name="originalPublicationYearMedian" select="document(concat($dataLocation, 'original_publication_year_MEDIAN.xml'))" />
    <xsl:variable name="originalPublicationYearRange" select="document(concat($dataLocation, 'original_publication_year_RANGE.xml'))" />

    <xsl:variable name="publicationYearMean" select="document(concat($dataLocation, 'publication_year_MEAN.xml'))" />
    <xsl:variable name="publicationYearMode" select="document(concat($dataLocation, 'publication_year_MODE.xml'))" />
    <xsl:variable name="publicationYearMedian" select="document(concat($dataLocation, 'publication_year_MEDIAN.xml'))" />
    <xsl:variable name="publicationYearRange" select="document(concat($dataLocation, '/publication_year_RANGE.xml'))" />
    
    <xsl:variable name="titleLengthMean" select="document(concat($dataLocation, 'title_length_MEAN.xml'))" />
    <xsl:variable name="titleLengthMode" select="document(concat($dataLocation, 'title_length_MODE.xml'))" />
    <xsl:variable name="titleLengthMedian" select="document(concat($dataLocation, 'title_length_MEDIAN.xml'))" />
    <xsl:variable name="titleLengthRange" select="document(concat($dataLocation, 'title_length_RANGE.xml'))" />
    
    <xsl:variable name="totalPagesMean" select="document(concat($dataLocation, 'total_pages_MEAN.xml'))" />
    <xsl:variable name="totalPagesMode" select="document(concat($dataLocation, 'total_pages_MODE.xml'))" />
    <xsl:variable name="totalPagesMedian" select="document(concat($dataLocation, 'total_pages_MEDIAN.xml'))" />
    <xsl:variable name="totalPagesRange" select="document(concat($dataLocation, 'total_pages_RANGE.xml'))" />
    
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
        <xsl:variable name="filename" select="concat($saveLocation, 'merged')" />
            
        <xsl:variable name="mergedXml" as="document-node()?">
            <xsl:document>
                <xsl:apply-templates select="user" mode="build.xml" />
            </xsl:document>
        </xsl:variable>
            
        <xsl:result-document href="{$filename}.xml" format="review" validation="strip">
            <xsl:copy-of select="$mergedXml" />
        </xsl:result-document>
        
        <xsl:result-document href="{$filename}.html" format="review" validation="strip">
            <html>
                <head>
                    <title>User ID: <xsl:value-of select="/user/@id" /></title>
                    <style type="text/css">
                        body, th, td {
                            font-size: 10px;                          
                        }
                        table {
                            width: 100%;
                            border-collapse: collapse;                          
                        }
                        td, th {
                            border: 1px solid #CFCFCF;  
                            text-align: center;
                            padding: .25em;
                        }
                        /*
                        tbody tr:nth-child(odd) {
                            background-color: #EFF6FF;
                        } 
                        */
                        td.recommended.true {
                            background-color: green;
                        }
                        td.accurate.true {
                            background-color: blue;
                        }
                    </style>
                </head>
                <body>                    
                    <h1>User ID: <xsl:value-of select="/user/@id" /></h1>
                    <xsl:apply-templates select="$mergedXml/user/recommendations[1]" mode="view" />
                </body>
            </html>            
        </xsl:result-document>
        
    </xsl:template>                
       
    <xsl:template match="user" mode="build.xml">
        <xsl:element name="{name()}">            
            <xsl:copy-of select="@*" />
            <xsl:copy-of select="books" />
            <xsl:copy-of select="recommendations" />
            
            <xsl:apply-templates select="$originalPublicationYearMean/user/recommendations" mode="merge" />
            <xsl:apply-templates select="$originalPublicationYearMode/user/recommendations" mode="merge" />
            <xsl:apply-templates select="$originalPublicationYearMedian/user/recommendations" mode="merge" />
            <xsl:apply-templates select="$originalPublicationYearRange/user/recommendations" mode="merge" />

            <xsl:apply-templates select="$publicationYearMean/user/recommendations" mode="merge" />
            <xsl:apply-templates select="$publicationYearMode/user/recommendations" mode="merge" />
            <xsl:apply-templates select="$publicationYearMedian/user/recommendations" mode="merge" />
            <xsl:apply-templates select="$publicationYearRange/user/recommendations" mode="merge" />
            
            <xsl:apply-templates select="$titleLengthMean/user/recommendations" mode="merge" />
            <xsl:apply-templates select="$titleLengthMode/user/recommendations" mode="merge" />
            <xsl:apply-templates select="$titleLengthMedian/user/recommendations" mode="merge" />
            <xsl:apply-templates select="$titleLengthRange/user/recommendations" mode="merge" />
            

            <xsl:apply-templates select="$totalPagesMean/user/recommendations" mode="merge" />
            <xsl:apply-templates select="$totalPagesMode/user/recommendations" mode="merge" />
            <xsl:apply-templates select="$totalPagesMedian/user/recommendations" mode="merge" />
            <xsl:apply-templates select="$totalPagesRange/user/recommendations" mode="merge" />
            
        </xsl:element>
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
   
   
    <xsl:template match="recommendations" mode="view">
        
        <table border="0" cellspacing="0" cellpadding="0">
            <thead>
            <tr>				
                <th rowspan="3">Goodreads ID</th>
                <th rowspan="3">Title</th>
                <th rowspan="3">Rating</th>
                <th rowspan="3">Originally Published</th>
                <th rowspan="3">Edition Published</th>
                <th rowspan="3">Total Pages</th>
                <th rowspan="3">ISBN-10</th>
                <th rowspan="3">ISBN-13</th>

                <xsl:for-each select="//recommendations[@factor = 'ALL']">
                    <xsl:sort select="@factor" data-type="text" order="ascending" />
                    
                    <th colspan="3"><xsl:value-of select="@factor" /></th>
                </xsl:for-each>
                <xsl:for-each-group select="//recommendations[@factor != 'ALL']" group-by="@factor">
                    <xsl:sort select="@factor" data-type="text" order="ascending" />
                    
                    <th colspan="12"><xsl:value-of select="@factor" /></th>
                </xsl:for-each-group>
            </tr>
            <tr>
                <xsl:for-each select="//recommendations[@factor = 'ALL']">
                    <xsl:sort select="@factor" data-type="text" order="ascending" />
                    <xsl:sort select="@measure" data-type="text" order="ascending" />
                    
                    <th colspan="3"><xsl:value-of select="@measure" /></th>
                </xsl:for-each>
                <xsl:for-each select="//recommendations[@factor != 'ALL']">
                    <xsl:sort select="@factor" data-type="text" order="ascending" />
                    <xsl:sort select="@measure" data-type="text" order="ascending" />
                    
                    <th colspan="3"><xsl:value-of select="@measure" /></th>
                </xsl:for-each>
            </tr>            
            <tr>				                                
                <xsl:for-each select="//recommendations">
                    <xsl:sort select="@factor" data-type="text" order="ascending" />
                    <xsl:sort select="@measure" data-type="text" order="ascending" />
                                               
                    <th class="recommended">R</th>
                    <th class="grade">G</th>
                    <th class="accurate">A</th>
                </xsl:for-each>
            </tr>         
            </thead>

            <tbody>
                <tr>
                    <td colspan="8">% Accurate Recommendations</td>
                    <xsl:apply-templates select="//recommendations[@factor != 'ALL']" mode="accuracy" />
                    <xsl:apply-templates select="//recommendations[@factor = 'ALL']" mode="accuracy" />
                </tr>
                <tr>
                    <td colspan="8">% Books Enjoyed</td>
                    <xsl:apply-templates select="//recommendations[@factor != 'ALL']" mode="popularity" />
                    <xsl:apply-templates select="//recommendations[@factor = 'ALL']" mode="popularity" />
                </tr>
                <tr>
                    <td colspan="8">% False Negatives for Popular</td>
                    <xsl:apply-templates select="//recommendations[@factor != 'ALL']" mode="false.negatives.liked" />
                    <xsl:apply-templates select="//recommendations[@factor = 'ALL']" mode="false.negatives.liked" />
                </tr>                
                <tr>
                    <td colspan="8">% False Positives for Mediocre and Unpopular</td>
                    <xsl:apply-templates select="//recommendations[@factor != 'ALL']" mode="false.positives.disliked" />
                    <xsl:apply-templates select="//recommendations[@factor = 'ALL']" mode="false.positives.disliked" />
                </tr>
               <xsl:apply-templates select="//books" mode="book.data" />
            </tbody>
            
            
            <!-- tr>
                <xsl:apply-templates select="//book">
                    <xsl:sort select="book/@rating" order="descending" data-type="number" />
                    <xsl:sort select="if (. &gt; 0) then 1 else 0" order="descending" data-type="number" />
                    <xsl:sort select="." order="descending" data-type="number" />					
                </xsl:apply-templates>
            </tr -->			
        </table>
        
    </xsl:template>
    
    <xsl:template match="recommendations" mode="accuracy">
        <td colspan="3"><xsl:value-of select="@accuracy" /></td>
    </xsl:template>
    
    <xsl:template match="recommendations" mode="popularity">
        <xsl:variable name="totalRated" select="count(recommendation)" />
        <xsl:variable name="totalPopular" select="count(recommendation[@book = //book[@rating &gt; 3]/@id])" />
        <xsl:variable name="result" select="if ($totalPopular &gt; 0) then sum(sum($totalPopular div $totalRated) * 100) else 0" />
                
        <td colspan="3"><xsl:value-of select="format-number($result, '##0.00')" /></td>
    </xsl:template>
    
    <xsl:template match="recommendations" mode="false.negatives.liked">        
        <xsl:variable name="popular" select="recommendation[@book = //book[@rating &gt; 3]/@id]" />
        <xsl:variable name="totalPopular" select="count($popular)" />
        <xsl:variable name="totalPopularUnrecommended" select="count($popular[. = 0])" />
        <xsl:variable name="result" select="if ($totalPopularUnrecommended &gt; 0) then sum(sum($totalPopularUnrecommended div $totalPopular) * 100) else 0" />
        
        <td colspan="3"><xsl:value-of select="format-number($result, '##0.00')" /></td>
    </xsl:template>
    
    <xsl:template match="recommendations" mode="false.positives.disliked">        
        <xsl:variable name="notPopular" select="recommendation[@book = //book[@rating &lt; 4]/@id]" />
        <xsl:variable name="totalNotPopular" select="count($notPopular)" />
        <xsl:variable name="totalNotPopularRecommended" select="count($totalNotPopular[. &gt; 0])" />
        <xsl:variable name="result" select="if ($totalNotPopularRecommended &gt; 0) then sum(sum($totalNotPopularRecommended div $totalNotPopular) * 100) else 0" />
        
        <td colspan="3"><xsl:value-of select="format-number($result, '##0.00')" /></td>
    </xsl:template>
    
    
    <xsl:template match="books" mode="book.data">        

                
            <xsl:apply-templates select="book">
                <xsl:sort select="@rating" order="descending" data-type="number" />
                <xsl:sort select="title" order="ascending" data-type="text" />
            </xsl:apply-templates>

            
    </xsl:template>   
        
    
    
    <xsl:template match="book">
            
        <tr>
            <td><xsl:value-of select="@id"/></td>
            <td><xsl:value-of select="title"/></td>            
            <td><xsl:value-of select="@rating" /></td>
            <td><xsl:value-of select="published[@original]/year"/></td>
            <td><xsl:value-of select="published[not(@original)]/year"/></td>
            <td><xsl:value-of select="pages"/></td>
            <td><xsl:value-of select="isbn[@version = '10']"/></td>
            <td><xsl:value-of select="isbn[@version = '13']"/></td>     

            <xsl:apply-templates select="//recommendation[parent::recommendations/@factor = 'ALL'][@book = current()/@id]">
                <xsl:sort select="@factor" data-type="text" order="ascending" />
                <xsl:sort select="@measure" data-type="text" order="ascending" />
            </xsl:apply-templates>        

            <xsl:apply-templates select="//recommendation[parent::recommendations/@factor != 'ALL'][@book = current()/@id]">
                <xsl:sort select="@factor" data-type="text" order="ascending" />
                <xsl:sort select="@measure" data-type="text" order="ascending" />
            </xsl:apply-templates>        
        </tr>
    </xsl:template>
    
    <xsl:template match="recommendation">
        <xsl:variable name="value" select="if (. &gt; 0) then 'Y' else 'N'" />
        
        <td class="recommended {if ($value = 'Y') then 'true' else 'false'}">
            <xsl:value-of select="$value" />
        </td>
        <td class="grade">
            <xsl:value-of select="." />
        </td>
        <td class="accurate {@accurate}">
            <xsl:value-of select="if (@accurate = 'true') then 'T' else 'F'" />            
        </td>
    </xsl:template>
    
    <xsl:template match="books" />  
    
  
    
</xsl:stylesheet>