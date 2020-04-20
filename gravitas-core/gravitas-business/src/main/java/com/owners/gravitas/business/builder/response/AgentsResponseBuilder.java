package com.owners.gravitas.business.builder.response;

import static com.owners.gravitas.util.StringUtils.convertObjectToString;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.google.api.client.util.ArrayMap;
import com.google.api.services.admin.directory.model.User;
import com.google.api.services.admin.directory.model.UserName;
import com.owners.gravitas.business.builder.AbstractBuilder;
import com.owners.gravitas.dto.Agent;
import com.owners.gravitas.dto.response.AgentsResponse;
import com.owners.gravitas.exception.ApplicationException;

/**
 * The Class AgentsResponseBuilder.
 *
 * @author harshads
 */
@Component
public class AgentsResponseBuilder extends AbstractBuilder< List< User >, AgentsResponse > {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger( AgentsResponseBuilder.class );

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.business.builder.AbstractBuilder#convertTo(java.lang.
     * Object, java.lang.Object)
     */
    @Override
    public AgentsResponse convertTo( final List< User > source, final AgentsResponse destination ) {
        AgentsResponse agentDetailsResponse = destination;
        if (source != null) {
            if (agentDetailsResponse == null) {
                agentDetailsResponse = new AgentsResponse();
            }
            for ( final User user : source ) {
                final Agent agent = new Agent();
                final UserName name = user.getName();
                agent.setFirstName( name.getGivenName() );
                agent.setLastName( name.getFamilyName() );
                agent.setEmail( user.getPrimaryEmail() );
                agent.setPhone( getUserPhone( user ) );
                final String state = getAgentState( user );
                final com.owners.gravitas.dto.UserAddress address = new com.owners.gravitas.dto.UserAddress();
                address.setState( state );
                agent.setAddress( address );

                agentDetailsResponse.getAgents().add( agent );

            }
        }
        return agentDetailsResponse;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.business.builder.AbstractBuilder#convertFrom(java.
     * lang.Object, java.lang.Object)
     */
    @Override
    public List< User > convertFrom( final AgentsResponse source, final List< User > destination ) {
        throw new UnsupportedOperationException( "convertFrom operation is not supported" );
    }

    /**
     * Gets the user phone.
     *
     * @param googleUser
     *            the google user
     * @return the user phone
     */
    private String getUserPhone( final User googleUser ) {
        String phone = null;
        final List< ArrayMap< String, Object > > phones = ( List< ArrayMap< String, Object > > ) googleUser.getPhones();
        if (phones != null) {
            for ( final ArrayMap< String, Object > phoneMap : phones ) {
                if ("work".equals( phoneMap.get( "type" ) )) {
                    phone = String.valueOf( phoneMap.get( "value" ) );
                    break;
                }
            }
        }
        return phone;
    }

    /**
     * Sets the agent state.
     *
     * @param source
     *            the source
     * @return the string
     */
    private String getAgentState( final User source ) {
        String state = "";
        final List< ArrayMap< String, Object > > addresses = ( List< ArrayMap< String, Object > > ) source
                .getAddresses();
        if (addresses != null) {
            for ( final ArrayMap< String, Object > addressMap : addresses ) {
                if ("home".equals( addressMap.get( "type" ) )) {
                    state = convertObjectToString( addressMap.get( "region" ) );
                    break;
                }
            }
        }
        return state;
    }

    /**
     * Build the Workbook Object for the Agent statistics
     * 
     * @param statisticsList
     * @param userMap
     * @return
     *         the Workbook Object for the Agent statistics
     */
    public Workbook createExcelReport( final List< Object[] > statisticsList, final Map< String, String > userMap ) {

        final Workbook workbook = new XSSFWorkbook();
        final CellStyle headerStyle = getHeaderCellStyle( workbook );
        final CellStyle oddCellStyle = getOddRowsCellStyle( workbook );
        final CellStyle evenCellStyle = getEvenRowsCellStyle( workbook );

        try {
            final Sheet sheet = workbook.createSheet( "Statistics" );

            // Creates Report Headers
            final String[] headers = { "Opportunity Id", "Opportunity Name", "Created Date", "F2F Date", "Agent Name" };
            createHeaders( sheet, headers, headerStyle );

            // Add records to Report
            short rowIndex = 1;
            
            for ( final Object[] objList : statisticsList ) {
                final CellStyle cellStyle = ( rowIndex % 2 == 0 ) ? evenCellStyle : oddCellStyle;
                final Row rowNew = sheet.createRow( rowIndex++ );

                setData( rowNew, 0, getStringValue( objList[5] ), cellStyle );
                setData( rowNew, 1, getStringValue( objList[2] ) + " " + getStringValue( objList[3] ), cellStyle );
                setData( rowNew, 2, getStringValue( objList.length==10?objList[9]:objList[8] ), cellStyle );
                setData( rowNew, 3, getStringValue( objList[4] ), cellStyle );
                setData( rowNew, 4, getUserNameBasedOnEmailId( getStringValue( objList[6] ), userMap ), cellStyle );
            }
            sheet.createFreezePane( 0, 1 );
        } catch ( final Exception e ) {
            throw new ApplicationException( e.getMessage(), e );
        }
        return workbook;
    }

    /**
     * Build the Workbook Object for the Agent statistics Count
     * 
     * @param statisticsList
     * @param userMaps
     * @return
     *         the Workbook Object for the Agent statistics Count
     */
    public Workbook createFaceToFaceCountExcel( final List< Object[] > statisticsList,
            final Map< String, String > userMap ) {

        final Workbook workbook = new XSSFWorkbook();
        final CellStyle headerStyle = getHeaderCellStyle( workbook );
        final CellStyle oddCellStyle = getOddRowsCellStyle( workbook );
        final CellStyle evenCellStyle = getEvenRowsCellStyle( workbook );

        try {
            final Sheet sheet = workbook.createSheet( "Statistics" );
            final String[] headers = { "Agent Name", "F2F Count" };
            createHeaders( sheet, headers, headerStyle );

            short rowIndex = 1;
            for ( final Object[] objList : statisticsList ) {
                final CellStyle cellStyle = ( rowIndex % 2 == 0 ) ? evenCellStyle : oddCellStyle;
                final Row row = sheet.createRow( rowIndex++ );
                setData( row, 0, getUserNameBasedOnEmailId( getStringValue( objList[2] ), userMap ), cellStyle );
                setData( row, 1, getStringValue( objList[0] ), cellStyle );
            }
            sheet.createFreezePane( 0, 1 );
        } catch ( final Exception e ) {
            throw new ApplicationException( e.getMessage(), e );
        }
        return workbook;
    }

    /**
     * Set the row data for the Excel sheet
     * 
     * @param row
     * @param coulumnNum
     * @param label
     * @param cellStyle
     */
    private void setData( final Row row, final int coulumnNum, final String label, final CellStyle cellStyle ) {
        final Cell cell = row.createCell( ( short ) coulumnNum );
        cell.setCellValue( label );
        cell.setCellStyle( cellStyle );
    }

    /**
     * Set the Header for the Excel sheet
     * 
     * @param sheet
     * @param headers
     * @param headerStyle
     */
    private void createHeaders( final Sheet sheet, final String[] headers, final CellStyle headerStyle ) {

        final String methodName = "createHeaders()";
        LOGGER.info( "Method starts :: " + methodName );

        final Row headerRow = sheet.createRow( 0 );
        Cell cell = null;

        for ( int index = 0; index < headers.length; index++ ) {
            cell = headerRow.createCell( index );
            cell.setCellValue( headers[index] );
            cell.setCellStyle( headerStyle );
        }
        LOGGER.info( "Method Ends :: " + methodName );
    }

    /**
     * Set the Border style for the Excel
     * 
     * @param workbook
     * @return
     */
    @SuppressWarnings( "deprecation" )
    private CellStyle getBorderCellStyle( final Workbook workbook ) {
        LOGGER.info( "Method starts :: getBorderCellStyle" );
        final CellStyle cellStyle = workbook.createCellStyle();

        cellStyle.setBorderBottom( CellStyle.BORDER_THIN );
        cellStyle.setBorderTop( CellStyle.BORDER_THIN );
        cellStyle.setBorderRight( CellStyle.BORDER_THIN );
        cellStyle.setBorderLeft( CellStyle.BORDER_THIN );

        cellStyle.setAlignment( XSSFCellStyle.ALIGN_CENTER );
        cellStyle.setVerticalAlignment( XSSFCellStyle.ALIGN_CENTER );
        cellStyle.setFillPattern( XSSFCellStyle.SOLID_FOREGROUND );
        LOGGER.info( "Method Ends :: getBorderCellStyle" );
        return cellStyle;
    }

    /**
     * Set the Odd Row cell style for the Excel
     * 
     * @param workbook
     * @return
     */
    private CellStyle getOddRowsCellStyle( final Workbook workbook ) {

        final String methodName = "getOddRowsCellStyle()";
        LOGGER.info( "Method starts :: " + methodName );

        final CellStyle cellStyle = getBorderCellStyle( workbook );
        cellStyle.setFillForegroundColor( ( short ) 44 );
        cellStyle.setLocked( false );

        LOGGER.info( "Method Ends :: " + methodName );

        return cellStyle;
    }

    /**
     * Set the Even Row cell style for the Excel
     * 
     * @param workbook
     * @return
     */
    private CellStyle getEvenRowsCellStyle( final Workbook workbook ) {

        final String methodName = "getEvenRowsCellStyle()";
        LOGGER.info( "Method starts :: " + methodName );

        final CellStyle cellStyle = getBorderCellStyle( workbook );

        cellStyle.setLocked( true );
        cellStyle.setFillForegroundColor( ( short ) 41 );

        LOGGER.info( "Method Ends :: " + methodName );

        return cellStyle;
    }

    /**
     * Set the Excel Header style
     * 
     * @param workbook
     * @return
     */
    @SuppressWarnings( "deprecation" )
    private CellStyle getHeaderCellStyle( final Workbook workbook ) {
        LOGGER.info( "Getting the headers for workbook" + workbook );

        final CellStyle cellStyle = getBorderCellStyle( workbook );
        cellStyle.setFillForegroundColor( IndexedColors.WHITE.getIndex() );

        cellStyle.setLocked( true );

        final Font font = workbook.createFont();
        font.setFontHeightInPoints( ( short ) 12 );
        font.setBoldweight( Font.BOLDWEIGHT_BOLD );
        cellStyle.setFont( font );

        LOGGER.info( "Cellstyle for workbook :: " + cellStyle );

        return cellStyle;
    }

    /**
     * Build the Workbook Object for the Executive F2F & statistics
     * 
     * @param faceToFaceCountList
     * @param statisticsList
     * @param userMap
     * @return
     *         the Workbook Object for the Executive F2F & statistics
     */
    public Workbook createExecutiveReport( final List< Object[] > faceToFaceCountList,
            final List< Object[] > statisticsList, final Map< String, String > userMap,
            final List< Object[] > agentsAndMBList ) {

        final Workbook workbook = new XSSFWorkbook();
        final CellStyle headerStyle = getHeaderCellStyle( workbook );
        final CellStyle oddCellStyle = getOddRowsCellStyle( workbook );
        final CellStyle evenCellStyle = getEvenRowsCellStyle( workbook );

        try {
            final Sheet faceToFaceCountsheet = workbook.createSheet( "FaceToFaceCount" );
            final Sheet statisticsheet = workbook.createSheet( "Statistics" );

            // Creates Report Headers
            final String[] faceToFaceCountHeaders = { "Managing Broker Name", "# New Opportunity in F2F" };
            final String[] statisticHeaders = { "Opportunity Id", "Opportunity Name", "Created Date", "F2F Date",
                    "Agent Name", "Managing Broker Name" };
            createHeaders( faceToFaceCountsheet, faceToFaceCountHeaders, headerStyle );
            createHeaders( statisticsheet, statisticHeaders, headerStyle );

            // Add records to faceToFaceCountsheet
            short rowIndex = 1;
            for ( final Object[] objList : faceToFaceCountList ) {
                final CellStyle cellStyle = ( rowIndex % 2 == 0 ) ? evenCellStyle : oddCellStyle;
                final Row rowNew = faceToFaceCountsheet.createRow( rowIndex++ );
                setData( rowNew, 0, getUserNameBasedOnEmailId( getStringValue( objList[2] ), userMap ), cellStyle );
                setData( rowNew, 1, getStringValue( objList[0] ), cellStyle );
            }
            faceToFaceCountsheet.createFreezePane( 0, 1 );

            // Prepare Map (AgentEmail -> MB Name)
            final Map< String, String > agentEmailIdAndMBNameMap = new HashMap< String, String >();
            agentsAndMBList.forEach( entry -> {
                agentEmailIdAndMBNameMap.put( getStringValue( entry[0] ),
                        getUserNameBasedOnEmailId( getStringValue( entry[1] ), userMap ) );
            } );

            // Add records to statisticsheet
            rowIndex = 1;
            for ( final Object[] objList : statisticsList ) {
                final CellStyle cellStyle = ( rowIndex % 2 == 0 ) ? evenCellStyle : oddCellStyle;
                final Row rowNew = statisticsheet.createRow( rowIndex++ );
                setData( rowNew, 0, getStringValue( objList[5] ), cellStyle );
                setData( rowNew, 1, getStringValue( objList[2] ) + " " + getStringValue( objList[3] ), cellStyle );
                setData( rowNew, 2, getStringValue( objList[9] ), cellStyle );
                setData( rowNew, 3, getStringValue( objList[4] ), cellStyle );
                setData( rowNew, 4, getUserNameBasedOnEmailId( getStringValue( objList[6] ), userMap ), cellStyle );
                setData( rowNew, 5, agentEmailIdAndMBNameMap.get( getStringValue( objList[6] ) ), cellStyle );
            }
            statisticsheet.createFreezePane( 0, 1 );

        } catch ( final Exception e ) {
            throw new ApplicationException( e.getMessage(), e );
        }
        return workbook;
    }

    /**
     * Gets the string value.
     *
     * @param object
     *            the object
     * @return the string value
     */
    private String getStringValue( final Object object ) {
        return ( null == object ) ? "" : object.toString();
    }

    /**
     * Gets the user name based on email id.
     *
     * @param emailId
     *            the email id
     * @param userMap
     *            the user map
     * @return the user name based on email id
     */
    private String getUserNameBasedOnEmailId( final String emailId, final Map< String, String > userMap ) {
        final String name = userMap.get( emailId );
        return name != null ? name : emailId;
    }
}
