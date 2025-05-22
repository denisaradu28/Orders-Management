package Presentation;

import BusinessLogicLayer.ClientBLL;
import BusinessLogicLayer.ProductBLL;
import BusinessLogicLayer.OrderBLL;
import BusinessLogicLayer.OrderItemBLL;


/**
 * The Controller class is responsible for initializing the application's main business logic layer (BLL)
 * components and linking them to the corresponding user interface panels within the View.
 * <p>
 *     It acts as a coordinator between the UI and business logic, wiring up event listeners
 *     to handel user actions for clients, products, and orders.
 * */

public class Controller {

    private final View view;

    private final ClientBLL clientBLL;
    private final ProductBLL productBLL;
    private final OrderBLL orderBLL;
    private final OrderItemBLL orderItemBLL;

    /**
     * Constructs a Controller object and initializes the BLL layers and user interface listeners.
     *
     * @param view the main application view containing the panels
     * */


    public Controller(View view) {
        this.view = view;

        clientBLL = new ClientBLL();
        productBLL = new ProductBLL();
        orderBLL = new OrderBLL();
        orderItemBLL = new OrderItemBLL();

        initClientPanel();
        initProductPanel();
        initOrderPanel();

    }

    /**
     * Initializes event listeners for the Client panel using the ClientBLL.
     * */

    private void initClientPanel() {
        view.getClientPanel().setListeners(clientBLL);
    }

    /**
     * Initializes event listeners for the Product panel using the ProductBLL.
     * */

    private void initProductPanel() {
        view.getProductPanel().setListeners(productBLL);
    }

    /**
     * Initializes event listeners for the Order panel using multiple BLL classes
     * including ClientBLL, ProductBLL, OrderBLL, and OrderItemBLL.
     */

    private void initOrderPanel() {
        view.getOrderPanel().setListeners(clientBLL, productBLL, orderBLL, orderItemBLL);
    }
}
