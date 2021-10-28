package dms.sales.view.bean.reports;

import dms.msil.common.constants.ReportsConstants;
import dms.msil.common.exceptions.MsilBusinessException;
import dms.msil.common.utils.ADFUtils;

import dms.msil.common.utils.JSFUtils;
import dms.msil.common.utils.MsilSessionScope;

import dms.sales.model.view.readonly.reports.ReportsQueryRORowImpl;

import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.nav.RichButton;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import javax.servlet.http.HttpSession;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.logging.ADFLogger;

import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.VariableValueManager;
import oracle.jbo.ViewCriteria;
import oracle.jbo.ViewObject;

import oracle.jbo.domain.Date;

import oracle.jbo.server.ViewObjectImpl;

import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

public class VehiclePriceListBean implements Serializable {
    @SuppressWarnings("compatibility:8906282295705158820")
    private static final long serialVersionUID = -8945706627733550931L;
    private static final String APP_ID = "sr_veh_pr_list";
    private static final String FILE_EXTENSION_CSV = ".csv";
    private String format;
    /**
     * MSIL ADF Logger for this class
     */
    private static final ADFLogger LOGGER = ADFLogger.createADFLogger(VehiclePriceListBean.class);
    
    private static final java.util.ResourceBundle bundle = java.util
                                                               .ResourceBundle
                                                               .getBundle("dms.bundle.ResourceBundle");
    private RichInputText fileNameBinding;
    private RichInputDate formatBinding;
    private RichSelectOneChoice formatBind;


    public VehiclePriceListBean() {
    }

--This line is added to practice git, i am writing a new method below
			public void helloWorld(){
//            session.setAttribute("principalMapCd", 1);
//            session.setAttribute("dealerMapCd", 10019);
//            session.setAttribute("parentGroupCd", "CHOWG");


//            session.setAttribute("parentGroupCd", "CHOWG");


            String reportName = (String) ADFUtils.evaluateEL("#{pageFlowScope.reportName}");
            LOGGER.info("inside init method, report name is ::: " + reportName);







			}
    public void initMethod() {
        try{
//            MsilSessionScope.setGlobalValues();
//            FacesContext context = FacesContext.getCurrentInstance();
//            HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
//            session.setAttribute("userId", "ranjan.pujari");
//            session.setAttribute("principalMapCd", 1);
//            session.setAttribute("dealerMapCd", 10019);
//            session.setAttribute("parentGroupCd", "CHOWG");
//            session.setAttribute("compCode", "CIL");
//            session.setAttribute("userCode", "EMP003");
           
            String reportName = (String) ADFUtils.evaluateEL("#{pageFlowScope.reportName}");
            LOGGER.info("inside init method, report name is ::: " + reportName);
            
            oracle.jbo.domain.Date currentDate = new oracle.jbo.domain.Date(oracle.jbo
                                                                                              .domain
                                                                                              .Date
                                                                                              .getCurrentDate());
            LOGGER.info("CurrentDate :: "+ currentDate);
            Row r = ADFUtils.findIterator("VehiclePriceListRO1Iterator").getCurrentRow();
            r.setAttribute("AsOnDate", currentDate);
            r.setAttribute("BalanceAmount", "0");
            DCIteratorBinding AmDealerLocItr = ADFUtils.findIterator("AmDealerLocRO1Iterator");
            ViewObjectImpl amDlrLoc = (ViewObjectImpl) AmDealerLocItr.getViewObject();
            ViewCriteria vc = amDlrLoc.getViewCriteriaManager().getViewCriteria("forCdVehiclePriceListROCriteria1");
//            VariableValueManager vvm = vc.ensureVariableManager();
            amDlrLoc.applyViewCriteria(vc);
            amDlrLoc.executeQuery();
            
//            Row r4 = ADFUtils.findIterator("AmDealerLocRO1Iterator").getCurrentRow();
//            String forcd = r4.getAttribute("ForCd").toString();
            
            DCIteratorBinding AmDealerLocROIter = (DCIteratorBinding) BindingContext.getCurrent()
                                                                                    .getCurrentBindingsEntry()
                                                                                    .get("AmDealerLocRO1Iterator");
            ViewObjectImpl AmDealerLocRO = (ViewObjectImpl) AmDealerLocROIter.getViewObject();
            String forcd = (String) AmDealerLocRO.getCurrentRow().getAttribute("ForCd");
            LOGGER.info("forcd is "+forcd);
            r.setAttribute("PriceListFor", forcd);
            r.setAttribute("SalesType", "All Sales Type");
          
            DCIteratorBinding dealerItr = ADFUtils.findIterator("VehiclePriceListDealerRO1Iterator");
            ViewObject viewObject = dealerItr.getViewObject();
            viewObject.executeQuery();
            Row[] row = dealerItr.getAllRowsInRange();
            System.out.println("Row values init" + row.length);
            row[1].setAttribute("SelectBoxDealer", true);
            String listCode = (String) row[1].getAttribute("ListCd");
            System.out.println("dlr null check list code" + listCode);
            
            
            DCIteratorBinding outletItrator = ADFUtils.findIterator("VehiclePriceListOutletRO1Iterator");
            ViewObject viewObject1 = outletItrator.getViewObject();
            VariableValueManager vvm = viewObject1.ensureVariableManager();
            vvm.setVariableValue("bindDealerMapCd", listCode);
            viewObject1.executeQuery();
            Row[] row1 = outletItrator.getAllRowsInRange();
            LOGGER.info("Row values init outlet" + row1.length);
            
            row1[1].setAttribute("SelectBoxOutlet", true);
            String listDbs = (String) row1[1].getAttribute("ListCd");

            r.setAttribute("DealerT", listCode);
            r.setAttribute("OutletT", listDbs);
            
            DCIteratorBinding ReportsQueryROItr = (DCIteratorBinding) BindingContext.getCurrent()
                                                                            .getCurrentBindingsEntry()
                                                                            .get("ReportsQueryRO1Iterator");
            ViewObject ReportsQueryRO = ReportsQueryROItr.getViewObject();
            ReportsQueryRO.executeQuery();
            Row r1 = ReportsQueryRO.first();
            r1.setAttribute("DestinationT", "S");
            r1.setAttribute("Format", "4");
            
        }
        catch (Exception ex) {
                    ex.printStackTrace();
                    throw new MsilBusinessException(ex.getMessage());
                }
    }
    public void submitReportAL(ActionEvent actionEvent) {
        try{
            LOGGER.info("enter into submitReportAL");
            String app_id = "sr_veh_pr_list";
//          String reportName = (String) ADFUtils.evaluateEL("#{pageFlowScope.reportName}");
            String reportName = "vehiclePriceListReport";
            
//            Checking if Dealer is selected
            ViewObject dlrVO = ADFUtils.findIterator("VehiclePriceListDealerRO1Iterator").getViewObject();
            Row[] selectedRows = dlrVO.getFilteredRows("SelectBoxDealer", true);
            LOGGER.info("selected rows in dealer" + selectedRows);
            if (selectedRows.length == 0) {
                LOGGER.info("dealer list null check");
                ADFUtils.showMessage(2, "Select the Dealer");
//                JSFUtils.addFacesErrorMessage(bundle.getString("PRESALES_OPENENQUIRYBEAN_ERROR_MSG_DEALER_DETAILS"));
                return;
            }
//            Checking if Outlet is selected
            ViewObject outletVO = ADFUtils.findIterator("VehiclePriceListOutletRO1Iterator").getViewObject();
            Row[] selectedoutRows = outletVO.getFilteredRows("SelectBoxOutlet", true);
            LOGGER.info("outlet query inquiry " + selectedoutRows);
            if (selectedoutRows.length == 0) {
                LOGGER.info("outlet list null check");
                ADFUtils.showMessage(2, "Select the Location");
//                JSFUtils.addFacesErrorMessage(bundle.getString("PRESALES_OPENENQUIRYBEAN_ERROR_MSG_OUTLET_DETAILS"));
                return;
            }
            
//          Checking if As on Date is entered
            DCIteratorBinding VehiclePriceListROIter = (DCIteratorBinding) BindingContext.getCurrent()
                                                                                .getCurrentBindingsEntry()
                                                                                .get("VehiclePriceListRO1Iterator");
            ViewObjectImpl VehiclePriceListRO = (ViewObjectImpl) VehiclePriceListROIter.getViewObject();
            oracle.jbo.domain.Date AsOnDate = (oracle.jbo.domain.Date) VehiclePriceListRO.getCurrentRow().getAttribute("AsOnDate");
            if(AsOnDate == null){
                ADFUtils.showMessage(2, "Enter As on Date");
                return;
//              JSFUtils.addFacesErrorMessage(bundle.getString("PRESALES_DELAYEDACTIONREPORT_BEAN_ERRORMSG_NOEXCELFORMAT"));
            }
            DCIteratorBinding reportsQueryROIter = (DCIteratorBinding) BindingContext.getCurrent()
                                                                                .getCurrentBindingsEntry()
                                                                                .get("ReportsQueryRO1Iterator");
            ViewObjectImpl reportsQueryRO = (ViewObjectImpl) reportsQueryROIter.getViewObject();
            String destination = (String) reportsQueryRO.getCurrentRow().getAttribute("DestinationT");
            String format = (String) reportsQueryRO.getCurrentRow().getAttribute("Format");
            LOGGER.info("destination & "+destination+" format::: "+format);
            
            if (reportName != null && "vehiclePriceListReport".equalsIgnoreCase(reportName)){
                LOGGER.info("about to call excel generation method");
                
                if(format.equals("4") && destination.equalsIgnoreCase("S")){
                    generateExcelVehiclePriceList();
                }
                if (format.equals("1") && destination.equalsIgnoreCase("S")) {
                    
                    ADFUtils.showMessage(2, "PDF/HTMLCSS/CHARACTER format does not exist for this particular report");
//                    JSFUtils.addFacesErrorMessage(bundle.getString("PRESALES_DELAYEDACTIONREPORT_BEAN_ERRORMSG_NOEXCELFORMAT"));
                }
                if (format.equals("2") && destination.equalsIgnoreCase("S")) {
                    
                    ADFUtils.showMessage(2, "PDF/HTMLCSS/CHARACTER format does not exist for this particular report");
//                    JSFUtils.addFacesErrorMessage(bundle.getString("PRESALES_DELAYEDACTIONREPORT_BEAN_ERRORMSG_NOEXCELFORMAT"));
                }
                if (format.equals("3") && destination.equalsIgnoreCase("S")) {
                    
                    ADFUtils.showMessage(2, "PDF/HTMLCSS/CHARACTER format does not exist for this particular report");
//                    JSFUtils.addFacesErrorMessage(bundle.getString("PRESALES_DELAYEDACTIONREPORT_BEAN_ERRORMSG_NOEXCELFORMAT"));
                }
                
            }
            LOGGER.info("outside excel generation method");
        }
        catch (Exception ex){
           ex.printStackTrace();
           throw new MsilBusinessException(ex.getMessage());
        }
    }
    
    /**
     * This method is used for excel generation of vehicle Price List Report.
     */
    private void generateExcelVehiclePriceList(){
        try{
            LOGGER.info("Inside generateExcelVehiclePriceList Method");
//          Code Commneted as Dealer & Outlet selections are not required for excel generation       
//           DCIteratorBinding dealerQueryROIter = (DCIteratorBinding) BindingContext.getCurrent()
//                                                                                                   .getCurrentBindingsEntry()
//                                                                                                   .get("VehiclePriceListDealerRO1Iterator");
//           ViewObjectImpl dealerQueryRO = (ViewObjectImpl) dealerQueryROIter.getViewObject();
//           String dealer = (String) dealerQueryRO.getCurrentRow().getAttribute("ListCd");
//           String dealer = (String) dealerQueryRO.getCurrentRow().getAttribute("ListCd");
//           LOGGER.info("dealer code is generating" + dealer);
//
//           DCIteratorBinding outletQueryROIter = (DCIteratorBinding) BindingContext.getCurrent()
//                                                                                                   .getCurrentBindingsEntry()
//                                                                                                   .get("VehiclePriceListOutletRO1Iterator");
//           ViewObjectImpl outletQueryRO = (ViewObjectImpl) outletQueryROIter.getViewObject();
//           String locCd = (String) outletQueryRO.getCurrentRow().getAttribute("ListCd");
//           LOGGER.info("location code is generating" + locCd);
//
             DCIteratorBinding dcIterator = (DCIteratorBinding) BindingContext.getCurrent()
                                                                                                     .getCurrentBindingsEntry()
                                                                                                     .get("VehiclePriceListRO1Iterator");
            String PriceListForCode = (String) dcIterator.getCurrentRow().getAttribute("PriceListForCode");
            LOGGER.info("PriceListForCode is passed" + PriceListForCode);
                
            String SalesTypeCode = (String) dcIterator.getCurrentRow().getAttribute("SalesTypeCode");
            LOGGER.info("SalesTypeCode is passed" + SalesTypeCode);
            
           oracle.jbo.domain.Date AsOnDate = (oracle.jbo.domain.Date)dcIterator.getCurrentRow().getAttribute("AsOnDate");
            LOGGER.info("AsOnDate is passed" + AsOnDate);
            if (AsOnDate != null) {
                AsOnDate = new oracle.jbo.domain.Date(AsOnDate.toString().substring(0, 10));
            }
            DCIteratorBinding VehiclePriceListExcelRO1Iter =
                        (DCIteratorBinding) BindingContext.getCurrent().getCurrentBindingsEntry().get("VehiclePriceListExcelRO1Iterator");
            ViewObjectImpl VehiclePriceListExcelRO =(ViewObjectImpl) VehiclePriceListExcelRO1Iter.getViewObject();
                    VehiclePriceListExcelRO.setNamedWhereClauseParam("bind_P_MUL_FOR",PriceListForCode );
                    VehiclePriceListExcelRO.setNamedWhereClauseParam("bind_P_SALCODE",SalesTypeCode );
                    VehiclePriceListExcelRO.setNamedWhereClauseParam("bind_p_fromdt",AsOnDate );
                    VehiclePriceListExcelRO.setNamedWhereClauseParam("bind_p_principal",
                                                                          ADFUtils.evaluateEL("#{sessionScope.principalMapCd}").toString());
                    
                    VehiclePriceListExcelRO.executeQuery();
 //                  LOGGER.info("query -->" + testDriveAllTeamLeadReportRO.getQuery());
                    LOGGER.info("row count is-->" + VehiclePriceListExcelRO.getEstimatedRowCount());
                    
                        RichButton btn = (RichButton) JSFUtils.findComponentInRoot("b2");
                        LOGGER.info("Rich Button id is ------"+getComponentClientIdFromId(btn, "b2"));
                        String submitid = getComponentClientIdFromId(btn, "b2");
                        FacesContext facesContext = FacesContext.getCurrentInstance();
                        ExtendedRenderKitService service = (ExtendedRenderKitService) org.apache.myfaces.trinidad.util.Service.getRenderKitService(facesContext, ExtendedRenderKitService.class);
                        service.addScript(facesContext, "callButtonAction('"+submitid+"');");
            
        }
        catch(Exception ex){
            ex.printStackTrace();
            throw new MsilBusinessException(ex.getMessage());
        }
    }
    
    
    /**
     * to get all laxical Dealer
     */
    public String getAllLaxicalDealers(){
        try{
            String lexicalParams = "";
                DCIteratorBinding dealerItr = (DCIteratorBinding) BindingContext.getCurrent()
                                                                                    .getCurrentBindingsEntry()
                                                                                    .get("VehiclePriceListDealerRO1Iterator");
                Boolean listCheckDealer = (Boolean) dealerItr.getCurrentRow().getAttribute("SelectBoxDealer");
                System.out.println("Dealer values are " + listCheckDealer);
            if (listCheckDealer != null) {
                if ("ALL".equals(listCheckDealer)) {
                    RowSetIterator DealerRSIter = dealerItr.getViewObject().createRowSetIterator(null);
                    while (DealerRSIter.hasNext()) {
                        if (lexicalParams != null) {
                           Row row =  DealerRSIter.next();
                           String rowVal = (String) row.getAttribute("ListCd");
                           rowVal = "'" + rowVal + "'" + ",";
                           lexicalParams = lexicalParams.concat(rowVal);
                           LOGGER.info("rowval-->" + rowVal);
                        }
                    }
                    DealerRSIter.closeRowSetIterator();
                    lexicalParams = lexicalParams.substring(0, lexicalParams.length() - 1);
                }
                else{
                    lexicalParams = (String) dealerItr.getCurrentRow().getAttribute("ListCd");
                    lexicalParams = "'" + lexicalParams + "'";
                }
            }
            LOGGER.info("laxicalParams-->" + lexicalParams);            
            return lexicalParams;
            }
        catch (Exception ex){
            ex.printStackTrace();
            throw new MsilBusinessException(ex.getMessage());
        }
    }
    /**
     * Getting all the Outlet Details
     */
    public String getAllLexicalOutlets(){
        try {
            String laxicalParams = "";
            DCIteratorBinding outletItr = (DCIteratorBinding) BindingContext.getCurrent()
                                                                                .getCurrentBindingsEntry()
                                                                                .get("VehiclePriceListOutletRO1Iterator");
            String listCheckOutlet = (String) outletItr.getCurrentRow().getAttribute("SelectBoxOutlet");
            System.out.println("Outlet values are printing " + listCheckOutlet);
            if (listCheckOutlet != null) {
                if ("ALL".equalsIgnoreCase(listCheckOutlet)) {
                    RowSetIterator outletRSIter = outletItr.getViewObject().createRowSetIterator(null);
                    while (outletRSIter.hasNext()) {
                        if (laxicalParams != null) {
                            Row row = outletRSIter.next();
                            String rowVal = (String) row.getAttribute("ListCd");
                            rowVal = "'" + rowVal + "'" + ",";
                            laxicalParams = laxicalParams.concat(rowVal);
                            LOGGER.info("rowval-->" + rowVal);
                        }
                    }
                    outletRSIter.closeRowSetIterator();
                    laxicalParams = laxicalParams.substring(0, laxicalParams.length() - 1);


                } else {
                    laxicalParams = (String) outletItr.getCurrentRow().getAttribute("ListCd");
                    laxicalParams = "'" + laxicalParams + "'";
                }

            }

            LOGGER.info("laxicalParams-->" + laxicalParams);
            return laxicalParams;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new MsilBusinessException(ex.getMessage());
        }
    }
    
    public void dealerVCL(ValueChangeEvent valueChangeEvent) {
        try{
            String laxicalParams = "";
            ArrayList<String> res = new ArrayList<String>();
            
            Row r = ADFUtils.findIterator("VehiclePriceListDealerRO1Iterator").getCurrentRow();
            LOGGER.info ("List Code " + r.getAttribute("ListCd"));
            DCIteratorBinding dc1 = ADFUtils.findIterator("VehiclePriceListDealerRO1Iterator");
            ViewObject vo1 = dc1.getViewObject();
            String all = (String) r.getAttribute("ListCd");
            
            if ((Boolean) valueChangeEvent.getNewValue()){
                if (all != null && "ALL".equalsIgnoreCase(all)){
                    RowSetIterator rsi = dc1.getViewObject().createRowSetIterator(null);
                    while (rsi.hasNext()) {
                        Row row2 = rsi.next();
                        row2.setAttribute("SelectBoxDealer", false);
                    //            System.out.println("while loop in the all" + row2);
                    }
                    rsi.closeRowSetIterator();
                    DCIteratorBinding dc = ADFUtils.findIterator("VehiclePriceListOutletRO1Iterator");
                    ViewObject vo = dc.getViewObject();
                    vo.setNamedWhereClauseParam("bindDealerMapCd", null);
                    vo.setNamedWhereClauseParam("bindReset", null);
                    vo.executeQuery();
                }
                else{
                    UIComponent c = valueChangeEvent.getComponent();
                    c.processUpdates(FacesContext.getCurrentInstance());
                    LOGGER.info("print in else part lexical parameters " + laxicalParams);
                    laxicalParams = (String) dc1.getCurrentRow().getAttribute("ListCd");

                    Row[] row = vo1.getAllRowsInRange();

                    Row[] rw = dc1.getViewObject().getFilteredRowsInRange("SelectBoxDealer", true);

                    LOGGER.info(" rw.length is  " + rw.length);
                    if (rw.length > 0){
                        for (Row rr : rw){
                            LOGGER.info("bean values selected are " + rr.getAttribute("ListCd"));
                            row[0].setAttribute("SelectBoxDealer", false);
                            if(!res.contains(rr.getAttribute("ListCd"))){
                                // added for remove the duplicate
                                res.add((String) rr.getAttribute("ListCd"));
                        }
                    }
                }
            }
        }
            else {
                laxicalParams = "";
                String unselectd_dlr_code = (String) dc1.getCurrentRow().getAttribute("ListCd");
                Row[] row = vo1.getAllRowsInRange();

                Row[] rw = dc1.getViewObject().getFilteredRowsInRange("SelectBoxDealer", true);
                LOGGER.info(" rw.length is  " + rw.length);
                    if (rw.length > 0) {
                        for (Row rr : rw) {
                //            System.out.println("bean values selected are " + rr.getAttribute("ListCd"));
                            row[0].setAttribute("SelectBoxDealer", false);
                            if (!((String) (rr.getAttribute("ListCd"))).equalsIgnoreCase(unselectd_dlr_code)) {
                                if(!res.contains(rr.getAttribute("ListCd"))){ // added for remove the duplicate
                                res.add((String) rr.getAttribute("ListCd"));
                                }
                            }
                        }
                    }
                }
               laxicalParams = null;
               ArrayList<String> res_unique = new ArrayList<String>();
               res_unique = removeDuplicates(res);
               LOGGER.info("unique values"+res_unique);
               String res_value = null;
            if(res_unique.size()>0){
                Iterator itr = res_unique.iterator();
                while (itr.hasNext()){
                       LOGGER.info("in while loop ");
                       String val = itr.next() + "";
                       LOGGER.info("itr.next() "+val);
                    //   res_value = val + "," + res_value;
                    //   System.out.println("print res_value outside " + res_value);
                      // res_value = res_value.substring(0, res_value.length() - 1);
                       if(laxicalParams == null){
                       laxicalParams = val;
                       }
                       else{
                         laxicalParams = laxicalParams + "," + val;
                         }
                    
                       System.out.println("dealer final laxicalparams in else " + laxicalParams);
                    
                        DCIteratorBinding dc = ADFUtils.findIterator("VehiclePriceListOutletRO1Iterator");
                        ViewObject vo = dc.getViewObject();
                        vo.setNamedWhereClauseParam("bindDealerMapCd", laxicalParams);
                        vo.setNamedWhereClauseParam("bindReset", null);
                        vo.executeQuery();
                    
                }
            }
            System.out.println("++++laxical params value in the dealer inquiry vcl"+laxicalParams);
            DCIteratorBinding dc2 = ADFUtils.findIterator("VehiclePriceListRO1Iterator");
            ViewObject vo3 = dc2.getViewObject();
            vo3.getCurrentRow().setAttribute("DealerT", laxicalParams);
            FacesContext facesContext = FacesContext.getCurrentInstance();
            ExtendedRenderKitService service =
                (ExtendedRenderKitService) org.apache
                                                                             .myfaces
                                                                             .trinidad
                                                                             .util
                                                                             .Service
                                                                             .getRenderKitService(facesContext,
                                                                                                  ExtendedRenderKitService.class);
            service.addScript(facesContext, "unBlockUI();");
        }
        catch (Exception ex) {
                    ex.printStackTrace();
                    throw new MsilBusinessException(ex.getMessage());
                }
    }

    public void outletVCL(ValueChangeEvent valueChangeEvent) {
        try{
            String laxicalParams = "";
            ArrayList<String> res = new ArrayList<String>();

            Row r = ADFUtils.findIterator("VehiclePriceListOutletRO1Iterator").getCurrentRow();
            LOGGER.info("List code is :::: " + r.getAttribute("ListCd"));
            DCIteratorBinding dc1 = ADFUtils.findIterator("VehiclePriceListOutletRO1Iterator");
            ViewObject vo1 = dc1.getViewObject();
            String all = (String) r.getAttribute("ListCd");
            if ((Boolean) valueChangeEvent.getNewValue()){
                laxicalParams = (String) vo1.getCurrentRow().getAttribute("ListCd");
                if (all != null && "ALL".equals(all)){
                    Row[] rww = dc1.getViewObject().getFilteredRows("SelectBoxOutlet", true);
                    if (rww.length > 0){
                        for (Row row : rww){
                            row.setAttribute("SelectBoxOutlet", false);
                        }
                    }
                }
                else{
                    UIComponent c = valueChangeEvent.getComponent();
                    c.processUpdates(FacesContext.getCurrentInstance());
                    System.out.println("print in else part lexical " + laxicalParams);
                        laxicalParams = (String) dc1.getCurrentRow().getAttribute("ListCd");

                    Row[] row = vo1.getAllRowsInRange();

                    Row[] rw = dc1.getViewObject().getFilteredRowsInRange("SelectBoxOutlet", true);

                    System.out.println(" rw.length is  " + rw.length);
                    if (rw.length > 0){
                        for (Row rr : rw){
                            System.out.println("bean values selected are " + rr.getAttribute("ListCd"));
                            row[0].setAttribute("SelectBoxOutlet", false);
                            if(!res.contains(rr.getAttribute("ListCd"))){
                                // added for remove the duplicate
                                res.add((String) rr.getAttribute("ListCd"));
                            }
                        }
                    }
                }
            }
            else{
                laxicalParams = "";
                String unselectd_dlr_code = (String) dc1.getCurrentRow().getAttribute("ListCd");
                Row[] row = vo1.getAllRowsInRange();

                Row[] rw = dc1.getViewObject().getFilteredRowsInRange("SelectBoxOutlet", true);

                System.out.println(" rw.length is  " + rw.length);
                if (rw.length > 0){
                    for (Row rr : rw){
                        System.out.println("bean values selected are " + rr.getAttribute("ListCd"));
                        row[0].setAttribute("SelectBoxOutlet", false);
                        if (!((String) (rr.getAttribute("ListCd"))).equalsIgnoreCase(unselectd_dlr_code)){
                            if(!res.contains(rr.getAttribute("ListCd"))){
                                // added for remove the duplicate
                                res.add((String) rr.getAttribute("ListCd"));
                            }
                        }
                    }
                    
                }
            }
            laxicalParams = null;
            ArrayList<String> res_unique = new ArrayList<String>();
            res_unique = removeDuplicates(res);
            System.out.println("unique values"+res_unique);
            String res_value = null;
            if(res_unique.size()>0){
                Iterator itr = res_unique.iterator();
                while (itr.hasNext()){
                    //   System.out.println("in while loop ");
                       String val = itr.next() + "";
                       System.out.println("itr.next() "+val);
                    //   res_value = val + "," + res_value;
                    //   System.out.println("print res_value outside " + res_value);
                      // res_value = res_value.substring(0, res_value.length() - 1);
                    if(laxicalParams == null){
                        laxicalParams = val;
                    }
                    else{
                      laxicalParams = laxicalParams + "," + val;
                      }
                    
                    System.out.println("final laxicalparams in else " + laxicalParams);
                    DCIteratorBinding dc = ADFUtils.findIterator("VehiclePriceListRO1Iterator");
                    ViewObject vo = dc.getViewObject();
                    //   vo.getCurrentRow().setAttribute("DealerT", null);
                    vo.getCurrentRow().setAttribute("OutletT", laxicalParams);
                }
                
            }
            System.out.println("laxical params value in the outlet inquiry vcl"+laxicalParams);
            DCIteratorBinding dc = ADFUtils.findIterator("VehiclePriceListRO1Iterator");
            ViewObject vo = dc.getViewObject();
            // String dealerVal = (String) vo.getCurrentRow().getAttribute("DealerT");
            // vo.getCurrentRow().setAttribute("DealerT", null);
            vo.getCurrentRow().setAttribute("OutletT", laxicalParams);
            FacesContext facesContext = FacesContext.getCurrentInstance();
            ExtendedRenderKitService service =
                (ExtendedRenderKitService) org.apache
                                                                             .myfaces
                                                                             .trinidad
                                                                             .util
                                                                             .Service
                                                                             .getRenderKitService(facesContext,
                                                                                                  ExtendedRenderKitService.class);
            service.addScript(facesContext, "unBlockUI();");
        }
        catch (Exception ex){
            ex.printStackTrace();
            throw new MsilBusinessException(ex.getMessage());
        }
    }

    public void destinationVCL(ValueChangeEvent valueChangeEvent) {
        LOGGER.info("inside DestinationVCL() " + valueChangeEvent.getNewValue());
//        String reportName = (String) ADFUtils.evaluateEL("#{pageFlowScope.reportName}");
        String reportName = "vehiclePriceListReport"; 
//        if (reportName == null) {
//            if (valueChangeEvent.getNewValue() != null && valueChangeEvent.getNewValue().equals("P")) {
//                DCIteratorBinding reportsQueryROItr = (DCIteratorBinding) BindingContext.getCurrent()
//                                                                                .getCurrentBindingsEntry()
//                                                                                .get("ReportsQueryRO1Iterator");
//                ReportsQueryRORowImpl rw = (ReportsQueryRORowImpl) reportsQueryROItr.getCurrentRow();
//                rw.setFormat("3");
//            }
//        }
            if (reportName != null && ("vehiclePriceListReport".equalsIgnoreCase(reportName))) {
                if (valueChangeEvent.getNewValue() != null && valueChangeEvent.getNewValue().equals("P")) {
                LOGGER.info("dest vcl code in the bean ");
                this.getFormatBind().setValue("3");
                this.getFormatBind().setDisabled(true);
                this.getFileNameBinding().setDisabled(true);
                }else{
                      DCIteratorBinding reportsQueryIter = ADFUtils.findIterator("ReportsQueryRO1Iterator");
                        ViewObjectImpl reportsQueryRO = (ViewObjectImpl) reportsQueryIter.getViewObject();
                        Row rr = reportsQueryRO.getCurrentRow();
                        rr.setAttribute("FileNameT", null);
                        this.getFormatBind().setValue("2");
                        this.getFormatBind().setDisabled(false);
                    }
            }
    }

    public void formatVCL(ValueChangeEvent valueChangeEvent) {
        try {
            LOGGER.info("executing formatVCL() ");
//            String reportName = (String) ADFUtils.evaluateEL("#{pageFlowScope.reportName}");
            String reportName = "vehiclePriceListReport"; 
            if (valueChangeEvent.getNewValue() != null){
                format = (String) valueChangeEvent.getNewValue();
                if (reportName != null && ("vehiclePriceListReport".equalsIgnoreCase(reportName))) {
                    DCIteratorBinding reportsQueryROIter = (DCIteratorBinding) BindingContext.getCurrent()
                                                                                        .getCurrentBindingsEntry()
                                                                                        .get("ReportsQueryRO1Iterator");
                    ViewObjectImpl reportsQueryRO = (ViewObjectImpl) reportsQueryROIter.getViewObject();
                    Row rrr = reportsQueryRO.getCurrentRow();
                    String destination = (String) reportsQueryRO.getCurrentRow().getAttribute("DestinationT");
                    String fName = (String) reportsQueryRO.getCurrentRow().getAttribute("FileNameT");
                    if(format.equals("4") && destination.equalsIgnoreCase("S")){
                        System.out.println("if statement is executed");
                        rrr.setAttribute("FileNameT", null);
                        this.getFileNameBinding().setDisabled(false);
                    }else{
                        System.out.println("else one is working");
                        rrr.setAttribute("FileNameT", null);
                        this.getFileNameBinding().setDisabled(true);
                        }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new MsilBusinessException(ex.getMessage());
        }
    }


    public static ArrayList<String> removeDuplicates(ArrayList<String> list)
        {
            // Create a new ArrayList
            ArrayList<String> newList = new ArrayList<String>();
            // Traverse through the first list
            for (String element : list) {
                // If this element is not present in newList
                // then add it
                if (!newList.contains(element)) {
     
                    newList.add(element);
                }
            }
     
            // return the new list
            return newList;
        }
    
    public static String getComponentClientIdFromId(UIComponent comp, String id) {
        if(comp!=null){
            StringBuilder clientId = new StringBuilder(comp.getClientId());  
            clientId = new StringBuilder(clientId.substring(0, clientId.lastIndexOf(":") + 1));
            return clientId.append(id).toString();
        }
        else{
            return null;
        }
    }


    public void setFileNameBinding(RichInputText fileNameBinding) {
        this.fileNameBinding = fileNameBinding;
    }

    public RichInputText getFileNameBinding() {
        return fileNameBinding;
    }

    public void setFormatBinding(RichInputDate formatBinding) {
        this.formatBinding = formatBinding;
    }

    public RichInputDate getFormatBinding() {
        return formatBinding;
    }

    public void setFormatBind(RichSelectOneChoice formatBind) {
        this.formatBind = formatBind;
    }

    public RichSelectOneChoice getFormatBind() {
        return formatBind;
    }
}
