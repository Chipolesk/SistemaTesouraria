package org.exemploTesouraria.service;

import org.exemploTesouraria.exception.DataConflictException;
import org.exemploTesouraria.exception.ResourceNotFoundException;
import org.exemploTesouraria.model.Canteen;
import org.exemploTesouraria.repository.CanteenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CanteenService {

    @Autowired
    private CanteenRepository canteenRepository;

    public Canteen createCanteen(String food, String description, double valueSold, Date dateCant, double expenses, String nameDebtors, String annotations) {
        Canteen insertCanteen = new Canteen();
        double profit = valueSold - expenses;

        if(canteenRepository.findByDateCant(dateCant).isPresent()) {
            throw DataConflictException.CanteenAlreadyExist(dateCant);
        }

        insertCanteen.setDateCant(dateCant);
        insertCanteen.setFood(food);
        insertCanteen.setDescription(description);
        insertCanteen.setValueSold(valueSold);
        insertCanteen.setAnnotations(annotations);
        insertCanteen.setExpenses(expenses);
        insertCanteen.setNameDebtors(nameDebtors);
        insertCanteen.setProfit(profit);

        return canteenRepository.save(insertCanteen);
    }
    public List<Canteen> findAllCanteens() {
        return canteenRepository.findAll();
    }
    public Canteen findByDateCant(Date dateCant) {

        return canteenRepository.findByDateCant(dateCant)
                .orElseThrow(() -> ResourceNotFoundException.CanteenNotFound(dateCant));
    }

}
