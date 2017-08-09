
/**
 * IWebServiceCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.1  Built on : Aug 31, 2011 (12:22:40 CEST)
 */

    package com.inssjp.mywebservice.business;

    /**
     *  IWebServiceCallbackHandler Callback class, Users can extend this class and implement
     *  their own receiveResult and receiveError methods.
     */
    public abstract class IWebServiceCallbackHandler{



    protected Object clientData;

    /**
    * User can pass in any object that needs to be accessed once the NonBlocking
    * Web service call is finished and appropriate method of this CallBack is called.
    * @param clientData Object mechanism by which the user can pass in user data
    * that will be avilable at the time this callback is called.
    */
    public IWebServiceCallbackHandler(Object clientData){
        this.clientData = clientData;
    }

    /**
    * Please use this constructor if you don't want to set any clientData
    */
    public IWebServiceCallbackHandler(){
        this.clientData = null;
    }

    /**
     * Get the client data
     */

     public Object getClientData() {
        return clientData;
     }

        
           /**
            * auto generated Axis2 call back method for sendMedicamentosFraccion method
            * override this method for handling normal response from sendMedicamentosFraccion operation
            */
           public void receiveResultsendMedicamentosFraccion(
                    com.inssjp.mywebservice.business.IWebServiceStub.SendMedicamentosFraccionResponseE result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from sendMedicamentosFraccion operation
           */
            public void receiveErrorsendMedicamentosFraccion(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getTransaccionesNoConfirmadas method
            * override this method for handling normal response from getTransaccionesNoConfirmadas operation
            */
           public void receiveResultgetTransaccionesNoConfirmadas(
                    com.inssjp.mywebservice.business.IWebServiceStub.GetTransaccionesNoConfirmadasResponseE result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getTransaccionesNoConfirmadas operation
           */
            public void receiveErrorgetTransaccionesNoConfirmadas(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for sendConfirmaTransacc method
            * override this method for handling normal response from sendConfirmaTransacc operation
            */
           public void receiveResultsendConfirmaTransacc(
                    com.inssjp.mywebservice.business.IWebServiceStub.SendConfirmaTransaccResponseE result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from sendConfirmaTransacc operation
           */
            public void receiveErrorsendConfirmaTransacc(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for sendCancelacTransacc method
            * override this method for handling normal response from sendCancelacTransacc operation
            */
           public void receiveResultsendCancelacTransacc(
                    com.inssjp.mywebservice.business.IWebServiceStub.SendCancelacTransaccResponseE result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from sendCancelacTransacc operation
           */
            public void receiveErrorsendCancelacTransacc(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for sendAlertaTransacc method
            * override this method for handling normal response from sendAlertaTransacc operation
            */
           public void receiveResultsendAlertaTransacc(
                    com.inssjp.mywebservice.business.IWebServiceStub.SendAlertaTransaccResponseE result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from sendAlertaTransacc operation
           */
            public void receiveErrorsendAlertaTransacc(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for sendMedicamentos method
            * override this method for handling normal response from sendMedicamentos operation
            */
           public void receiveResultsendMedicamentos(
                    com.inssjp.mywebservice.business.IWebServiceStub.SendMedicamentosResponseE result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from sendMedicamentos operation
           */
            public void receiveErrorsendMedicamentos(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getConsultaStock method
            * override this method for handling normal response from getConsultaStock operation
            */
           public void receiveResultgetConsultaStock(
                    com.inssjp.mywebservice.business.IWebServiceStub.GetConsultaStockResponseE result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getConsultaStock operation
           */
            public void receiveErrorgetConsultaStock(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getEnviosPropiosAlertados method
            * override this method for handling normal response from getEnviosPropiosAlertados operation
            */
           public void receiveResultgetEnviosPropiosAlertados(
                    com.inssjp.mywebservice.business.IWebServiceStub.GetEnviosPropiosAlertadosResponseE result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getEnviosPropiosAlertados operation
           */
            public void receiveErrorgetEnviosPropiosAlertados(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for sendCancelacTransaccParcial method
            * override this method for handling normal response from sendCancelacTransaccParcial operation
            */
           public void receiveResultsendCancelacTransaccParcial(
                    com.inssjp.mywebservice.business.IWebServiceStub.SendCancelacTransaccParcialResponseE result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from sendCancelacTransaccParcial operation
           */
            public void receiveErrorsendCancelacTransaccParcial(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getTransaccionesWS method
            * override this method for handling normal response from getTransaccionesWS operation
            */
           public void receiveResultgetTransaccionesWS(
                    com.inssjp.mywebservice.business.IWebServiceStub.GetTransaccionesWSResponseE result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getTransaccionesWS operation
           */
            public void receiveErrorgetTransaccionesWS(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getCatalogoElectronicoByGTIN method
            * override this method for handling normal response from getCatalogoElectronicoByGTIN operation
            */
           public void receiveResultgetCatalogoElectronicoByGTIN(
                    com.inssjp.mywebservice.business.IWebServiceStub.GetCatalogoElectronicoByGTINResponseE result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getCatalogoElectronicoByGTIN operation
           */
            public void receiveErrorgetCatalogoElectronicoByGTIN(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getCatalogoElectronicoByGLN method
            * override this method for handling normal response from getCatalogoElectronicoByGLN operation
            */
           public void receiveResultgetCatalogoElectronicoByGLN(
                    com.inssjp.mywebservice.business.IWebServiceStub.GetCatalogoElectronicoByGLNResponseE result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getCatalogoElectronicoByGLN operation
           */
            public void receiveErrorgetCatalogoElectronicoByGLN(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for sendMedicamentosDHSerie method
            * override this method for handling normal response from sendMedicamentosDHSerie operation
            */
           public void receiveResultsendMedicamentosDHSerie(
                    com.inssjp.mywebservice.business.IWebServiceStub.SendMedicamentosDHSerieResponseE result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from sendMedicamentosDHSerie operation
           */
            public void receiveErrorsendMedicamentosDHSerie(java.lang.Exception e) {
            }
                


    }
    