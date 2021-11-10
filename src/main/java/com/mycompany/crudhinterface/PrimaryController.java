package com.mycompany.crudhinterface;
/**/
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import org.hibernate.Session;
import models.Pedidos;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
/**
 ** FXML Controller class
 *
 ** @author Ridouan Abdellah Tieb
 *
 */
public class PrimaryController implements Initializable {

    @FXML
    private VBox caja;
    @FXML
    private AnchorPane subcaja;
    @FXML
    private Label titulo;
    @FXML
    private Label textoPedidos;
    @FXML
    private TableView<Pedidos> tablaPedidos;
    @FXML
    private TableColumn<Pedidos, Integer> numeroPedidos;
    @FXML
    private TableColumn<Pedidos, String> usuarioPedidos;
    @FXML
    private TableColumn<Pedidos, Date> fechaPedidos;
    @FXML
    private TableColumn<Pedidos, String> cicloPedidos;
    @FXML
    private TableColumn<Pedidos, Integer> estadoPedidos;

    /*
    Creo la conversion de la fecha de java.sql.Date a java.util.Date
     */
    java.util.Date utilDate = new java.util.Date();
    long lnMilisegundos = utilDate.getTime();
    java.sql.Date sqlDate = new java.sql.Date(lnMilisegundos);

    /*
    Abro la sesión.
     */
    Session s = HibernateUtil.getSessionFactory().openSession();

    /*        
     * Inicializo la clase controladora. 
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        /*
        Declaro una variable tipo Query en ella crearé las consulta.  
         */
        Query hoy = s.createQuery("FROM Pedidos WHERE fecha =:fecha AND estado =:estado");

        /*
        Enlazo las variables del diseño con las columnas de la tabla.
         */
        numeroPedidos.setCellValueFactory(new PropertyValueFactory<>("id"));
        cicloPedidos.setCellValueFactory(new PropertyValueFactory<>("ciclo"));
        usuarioPedidos.setCellValueFactory(new PropertyValueFactory<>("usuario"));
        estadoPedidos.setCellValueFactory(new PropertyValueFactory<>("estado"));
        fechaPedidos.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        /*
        Creo el Observable. 
         */
        ObservableList<Pedidos> lista = FXCollections.observableArrayList();
        /*
        Modifico los parámetros de la query. 
         */
        hoy.setParameter("fecha", sqlDate);
        hoy.setParameter("estado", 0);

        /*
        Añado al observale las variables correspondientes a las columnas. 
         */
        lista.addAll(hoy.list());

        /*
        Añado los items a la variable que corresponde a la tabla en Scene Builder.
         */
        tablaPedidos.setItems(lista);

        /*
        Imprimo la lista por consola como debuger.
         */
        System.out.println(hoy.list());


    }

    /*
    Creo el evento al clicar en un pedido para cambiar el estado del pedido. 
    */
    @FXML
    private void recoger(MouseEvent event) {
        Query qc = s.createQuery("UPDATE Pedidos SET estado =:estado WHERE id =:id");
        int recogiendo = tablaPedidos.getSelectionModel().getSelectedItem().getId(); 
        qc.setParameter("id", recogiendo);
        qc.setParameter("estado", 0);
        
        Transaction t = s.beginTransaction();
        t.commit();
    }
}
