package com.owners.gravitas.service.impl;


import static com.owners.gravitas.constants.Constants.GRAVITAS_ERROR_LOGGING_CONFIG_DIR;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.owners.gravitas.constants.Constants;
import com.owners.gravitas.error.log.CacheManager;
import com.owners.gravitas.error.log.CachedObject;
import com.owners.gravitas.service.ErrorLogService;
import com.owners.gravitas.util.DateUtil;

/**
 * The Class ErrorLogServiceImpl.
 *
 * @author shivamm
 */
@Service
public class ErrorLogServiceImpl extends BaseGoogleService implements ErrorLogService {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger( ErrorLogServiceImpl.class );

    /** The slack service impl. */
    @Autowired
    private SlackServiceImpl slackServiceImpl;

    /** The error log file path. */
    @Value( "${error.log.file.path}" )
    private String errorLogFilePath;

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.service.ErrorLogService#publishErrorReportOnSlack()
     */
    @Override
    public void publishErrorReportOnSlack() {
        String errorFilePath;
        try {
            errorFilePath = GRAVITAS_ERROR_LOGGING_CONFIG_DIR + errorLogFilePath + com.owners.gravitas.constants.Constants.BLANK_SPACE
                    + DateUtil.toString( new DateTime(), DateUtil.DEFAULT_CRM_DATE_PATTERN ) + ".xls";
            CachedObject cache = ( CachedObject ) CacheManager.getCache( new Long( 1234 ) );
            if (cache != null) {
                Map< String, Integer > errorMap = ( Map ) cache.object;
                LOGGER.debug( "Publish error report on slack for map size " + errorMap.size() );
                HSSFWorkbook workbook = null;
                File file = new File( errorFilePath );
                if (file.exists()) {
                    FileInputStream fileIn = new FileInputStream( file );
                    workbook = ( HSSFWorkbook ) WorkbookFactory.create( fileIn );

                    HSSFSheet worksheet1 = workbook.createSheet( "Error name and count" );
                    int rowNumWorksheet1 = 0;
                    HSSFRow row = worksheet1.createRow( ( short ) rowNumWorksheet1 );
                    setColumnHeaders( row, 0, "Error Name", workbook, worksheet1 );
                    setColumnHeaders( row, 1, "Count", workbook, worksheet1 );
                    rowNumWorksheet1++;
                    for ( Map.Entry< String, Integer > errorRow : errorMap.entrySet() ) {
                        HSSFRow rowNew = worksheet1.createRow( ( short ) rowNumWorksheet1 );
                        setData( rowNew, 0, errorRow.getKey(), workbook, worksheet1 );
                        setData( rowNew, 1, errorRow.getValue() + "", workbook, worksheet1 );
                        rowNumWorksheet1++;
                    }
                    FileOutputStream fileOut = new FileOutputStream( file );
                    workbook.write( fileOut );
                    fileOut.flush();
                    fileOut.close();
                    LOGGER.debug( "File writing complete for map size " + errorMap.size() );
                    slackServiceImpl.postExcelToSlack( file );
                    cache.expireCache();
                }
            }
        } catch ( FileNotFoundException e ) {
            LOGGER.error( e.getLocalizedMessage(), e );
        } catch ( IOException e ) {
            LOGGER.error( e.getLocalizedMessage(), e );
        } catch ( Exception e ) {
            LOGGER.error( e.getLocalizedMessage(), e );
        }
    }

    /**
     * Sets the data.
     *
     * @param row
     *            the row
     * @param coulumnNum
     *            the coulumn num
     * @param label
     *            the label
     * @param workbook
     *            the workbook
     * @param worksheet1
     *            the worksheet 1
     */
    private void setData( HSSFRow row, int coulumnNum, String label, HSSFWorkbook workbook, HSSFSheet worksheet1 ) {
        HSSFCell cell1 = row.createCell( ( short ) coulumnNum );
        cell1.setCellValue( label );
    }

    /**
     * Gets the column headers.
     *
     * @param row
     *            the row
     * @param coulumnNum
     *            the coulumn num
     * @param label
     *            the label
     * @param workbook
     *            the workbook
     * @param worksheet1
     *            the worksheet 1
     * @return the column headers
     */
    private void setColumnHeaders( HSSFRow row, int coulumnNum, String label, HSSFWorkbook workbook,
            HSSFSheet worksheet1 ) {
        HSSFCell cell1 = row.createCell( ( short ) coulumnNum );
        cell1.setCellValue( label );
        HSSFCellStyle cellStylae = workbook.createCellStyle();
        cellStylae.setFillForegroundColor( HSSFColor.GOLD.index );
        cellStylae.setFillPattern( HSSFCellStyle.SOLID_FOREGROUND );
        cell1.setCellStyle( cellStylae );

    }
}
