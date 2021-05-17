package sma.grupo3.Retailer.Agents.Warehouse;

import BESA.Kernel.Agent.StateBESA;
import sma.grupo3.Retailer.SharedDomain.Catalog;
import sma.grupo3.Retailer.SharedDomain.CustomerOrder;

import java.util.List;
import java.util.Map;

public class WarehouseState extends StateBESA {
    Map<Catalog, Integer> stock;
    List<CustomerOrder> customerOrders;
}
