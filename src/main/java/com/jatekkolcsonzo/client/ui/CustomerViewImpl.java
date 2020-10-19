package com.jatekkolcsonzo.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.jatekkolcsonzo.client.DTOEntity.CustomerEntityDTO;
import com.jatekkolcsonzo.client.place.GamePlace;
import com.jatekkolcsonzo.client.place.ReservationPlace;
import com.jatekkolcsonzo.client.services.CustomerServiceAsync;
import com.jatekkolcsonzo.server.services.CustomerServiceImpl;
import gwt.material.design.addins.client.window.MaterialWindow;
import gwt.material.design.client.constants.*;
import gwt.material.design.client.ui.*;
import gwt.material.design.client.ui.animate.MaterialAnimation;
import gwt.material.design.client.ui.animate.Transition;
import gwt.material.design.client.ui.table.MaterialDataTable;
import gwt.material.design.client.ui.table.cell.TextColumn;
import gwt.material.design.client.ui.table.cell.WidgetColumn;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Marton Kovacs
 * @since 2019-12-10
 */
public class CustomerViewImpl extends Composite implements CustomerView {

    interface MyUiBinder extends UiBinder<Widget, CustomerViewImpl> {}
    private static final CustomerViewImpl.MyUiBinder uiBinder = GWT.create(CustomerViewImpl.MyUiBinder.class);

    private Presenter listener;
    private String name;

    private CustomerServiceAsync customerServiceAsync = CustomerServiceImpl.App.getInstance();

    private List<CustomerEntityDTO> customers = new ArrayList<>();

    @UiField
    MaterialNavBar navBar;

    @UiField
    MaterialDataTable<CustomerEntityDTO> dataTable;

    @UiField
    MaterialButton fabButton;

    @UiField
    MaterialWindow addWindow;

    @UiField
    MaterialTextBox customerName;

    @UiField
    MaterialTextBox customerAddress;

    @UiField
    MaterialTextBox customerPhoneNumber;

    @UiField
    MaterialTextBox customerEmail;

    @UiField
    MaterialButton addButton;

    @UiField
    MaterialWindow deleteWindow;

    @UiField
    MaterialButton noButton;

    @UiField
    MaterialButton yesButton;

    public CustomerViewImpl(){
        initWidget(uiBinder.createAndBindUi(this));

        setupFabButton();

        setupAddButton();

        setDataTable();

        refreshTable();
    }

    @UiHandler("customerRefresh")
    void onClickRefresh(ClickEvent e){
        getAllCustomers();
    }

    @UiHandler("gameLink")
    void onClickGame(ClickEvent e){
        listener.goTo((new GamePlace(name)));
    }

    @UiHandler("reservationLink")
    void onClickReservation(ClickEvent e){
        listener.goTo((new ReservationPlace(name)));
    }

    @Override
    public void setPresenter(Presenter listener) {
        this.listener = listener;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    private void animate(MaterialWindow pWindow, Transition pTransition){
        MaterialAnimation animation = new MaterialAnimation();
        animation.setTransition(pTransition);
        animation.setDelay(0);
        animation.setDuration(500);
        animation.setInfinite(false);
        animation.animate(pWindow);
    }

    private void setupFabButton(){
        fabButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                addWindow.open();
                animate(addWindow, Transition.FADEINDOWNBIG);
            }
        });
    }

    private void setupAddButton(){
        addButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                CustomerEntityDTO customer = new CustomerEntityDTO();
                customer.setName(customerName.getText());
                customer.setAddress(customerAddress.getText());
                customer.setEmail(customerEmail.getText());
                customer.setPhoneNumber(customerPhoneNumber.getText());
                if(checkIfTextIsEmpty()) {
                    MaterialToast.fireToast("Please fill in all gaps", "rounded");
                } else if(checkEmail(customer)) {
                    MaterialToast.fireToast("Customer is already exists with this Email", "rounded");
                } else if(checkAddress(customer)) {
                    MaterialToast.fireToast("Customer is already exists with this Address", "rounded");
                } else if(checkNumber(customer)){
                    MaterialToast.fireToast("Customer is already exists with this Phone Number", "rounded");
                } else {
                    customers.add(customer);
                    addCustomer(customer);
                    clearTextBoxes();
                    MaterialToast.fireToast("Customer has been added", "rounded");
                    animate(addWindow,Transition.FADEOUT);
                    closeWindow(addWindow);
                }
                refreshTable();

            }
        });
    }

    private Boolean checkEmail(CustomerEntityDTO pCustomer){
        for(CustomerEntityDTO customer : customers){
            if (pCustomer.getEmail().toLowerCase().equals(customer.getEmail().toLowerCase())){
                return true;
            }
        }
        return false;
    }

    private Boolean checkAddress(CustomerEntityDTO pCustomer){
        for(CustomerEntityDTO customer : customers){
            if (pCustomer.getAddress().equals(customer.getAddress())){
                return true;
            }
        }
        return false;
    }

    private Boolean checkNumber(CustomerEntityDTO pCustomer){
        for(CustomerEntityDTO customer : customers){
            if (pCustomer.getPhoneNumber().equals(customer.getPhoneNumber())){
                return true;
            }
        }
        return false;
    }

    private Boolean checkIfTextIsEmpty(){
        return customerName.getText().equals("") || customerAddress.getText().equals("") || customerEmail.getText().equals("") || customerPhoneNumber.getText().equals("");
    }

    private void setDataTable(){
        dataTable.getTableTitle().setText("Customers");
        dataTable.getTableIcon().setIconType(IconType.GROUP);

        dataTable.addColumn(new TextColumn<CustomerEntityDTO>() {
            @Override
            public String getValue(CustomerEntityDTO customerEntityDTO) {
                return "";
            }
        }, "");

        dataTable.addColumn(new TextColumn<CustomerEntityDTO>() {
            @Override
            public String getValue(CustomerEntityDTO customerEntityDTO) {
                return customerEntityDTO.getName();
            }
        }, "Name");

        dataTable.addColumn(new TextColumn<CustomerEntityDTO>() {
            @Override
            public String getValue(CustomerEntityDTO customerEntityDTO) {
                return customerEntityDTO.getAddress();
            }
        }, "Address");

        dataTable.addColumn(new TextColumn<CustomerEntityDTO>() {
            @Override
            public String getValue(CustomerEntityDTO customerEntityDTO) {
                return customerEntityDTO.getPhoneNumber();
            }
        }, "Phone Number");

        dataTable.addColumn(new TextColumn<CustomerEntityDTO>() {
            @Override
            public String getValue(CustomerEntityDTO customerEntityDTO) {
                return customerEntityDTO.getEmail();
            }
        }, "Email address");

        dataTable.addColumn(new WidgetColumn<CustomerEntityDTO, MaterialCheckBox>() {
            @Override
            public MaterialCheckBox getValue(CustomerEntityDTO customerEntityDTO) {
                MaterialCheckBox mcb = new MaterialCheckBox();
                mcb.setEnabled(false);

                if(customerEntityDTO.hasReservation()){
                    mcb.setValue(true);
                } else {
                    mcb.setValue(false);
                }
                return mcb;
            }
        }, "Reservation");

        dataTable.addColumn(new WidgetColumn<CustomerEntityDTO, MaterialButton>() {

            @Override
            public MaterialButton getValue(CustomerEntityDTO customer) {
                MaterialButton btn = new MaterialButton();
                btn.setBackgroundColor(Color.BLUE);
                btn.setWaves(WavesType.DEFAULT);
                btn.setType(ButtonType.FLOATING);
                if(customer.hasReservation()){
                    btn.setEnabled(false);
                } else {
                    btn.setEnabled(true);
                }
                setDeleteButton(btn, customer);
                return btn;
            }
        }, "");
    }

    private void addCustomer(CustomerEntityDTO pCustomer){
        AsyncCallback<Void> customerDTOAsyncCallback = new AsyncCallback<Void>() {

            @Override
            public void onFailure(Throwable caught) {

            }

            @Override
            public void onSuccess(Void result) {

            }
        };
        customerServiceAsync.addCustomer(pCustomer,customerDTOAsyncCallback);
    }

    public void getAllCustomers(){

        AsyncCallback<List<CustomerEntityDTO>> customerEntityDTOAsyncCallback = new AsyncCallback<List<CustomerEntityDTO>>() {
            @Override
            public void onFailure(Throwable caught) {
            }

            @Override
            public void onSuccess(List<CustomerEntityDTO> result) {
                customers = result;
                dataTable.setVisibleRange(0, customers.size());
                dataTable.setRowData(0, customers);
            }
        };

        customerServiceAsync.getAllCustomers(customerEntityDTOAsyncCallback);
    }

    private void refreshTable(){
        dataTable.setVisibleRange(0, customers.size());
        dataTable.setRowData(0, customers);
        dataTable.getView().setRedraw(true);
        dataTable.getView().refresh();
    }

    private void clearTextBoxes(){
        customerName.setText("");
        customerPhoneNumber.setText("");
        customerEmail.setText("");
        customerAddress.setText("");
    }

    private void closeWindow(MaterialWindow pWindow){
        Timer timer = new Timer() {
            @Override
            public void run() {
                pWindow.close();
            }
        };
        timer.schedule(500);
    }

    private void deleteCustomer(String pEmail){
        AsyncCallback<Void> customerDTOAsyncCallback = new AsyncCallback<Void>() {
            @Override
            public void onFailure(Throwable caught) {
            }

            @Override
            public void onSuccess(Void result) {
            }
        };
        customerServiceAsync.deleteCustomer(pEmail,customerDTOAsyncCallback);
    }

    private void setDeleteButton(MaterialButton pButton, CustomerEntityDTO pCustomer){
        pButton.setSize(ButtonSize.MEDIUM);
        pButton.setIconType(IconType.DELETE);
        pButton.setText("Delete");
        pButton.setTooltip("Delete " + pCustomer.getName());
        pButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {

                deleteWindow.open();
                animate(deleteWindow,Transition.FADEINDOWNBIG);
                setYesButton(pCustomer);
                setNoButton();
            }
        });

    }

    private void setYesButton(CustomerEntityDTO pCustomer){
        yesButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                animate(deleteWindow,Transition.FADEOUT);
                closeWindow(deleteWindow);

                deleteCustomer(pCustomer.getEmail());
                customers.remove(pCustomer);
                refreshTable();
                MaterialToast.fireToast("Customer has been deleted", "rounded");
            }
        });
    }

    private void setNoButton(){
        noButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                animate(deleteWindow,Transition.FADEOUT);
                closeWindow(deleteWindow);
            }
        });
    }
}
