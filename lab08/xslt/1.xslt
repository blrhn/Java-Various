<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="html" indent="yes" encoding="UTF-8"/>

    <xsl:template match="/">
        <html>
            <head>
                <title>Kursy walut</title>
                <style>
                    table {
                        border-collapse: collapse;
                        width: 50%;
                    }
                    th, td {
                        border: 1px solid black;
                        padding: 8px;
                        text-align: left;
                    }
                    th {
                        background-color: #f2f2f2;
                    }
                </style>
            </head>
            <body>
                <h2><xsl:value-of select="channel/title"/></h2>
                <p>Baza: <xsl:value-of select="channel/baseCurrency"/></p>

                <table>
                    <tr>
                        <th>Waluta docelowa</th>
                        <th>Kurs</th>
                    </tr>
                    <xsl:for-each select="channel/item">
                        <tr>
                            <td><xsl:value-of select="targetName"/> (<xsl:value-of select="targetCurrency"/>)</td>
                            <td><xsl:value-of select="exchangeRate"/></td>
                        </tr>
                    </xsl:for-each>
                </table>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>