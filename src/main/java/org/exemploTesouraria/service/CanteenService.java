package org.exemploTesouraria.service;

import org.exemploTesouraria.DTO.DebtorDTO;
import org.exemploTesouraria.DTO.DebtorWithCanteenDTO;
import org.exemploTesouraria.exception.DataConflictException;
import org.exemploTesouraria.exception.ResourceNotFoundException;
import org.exemploTesouraria.model.Canteen;
import org.exemploTesouraria.model.Debtors;
import org.exemploTesouraria.repository.CanteenRepository;
import org.exemploTesouraria.repository.DebtorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CanteenService {

    private final CanteenRepository canteenRepository;
    private final DebtorRepository debtorRepository;

    public CanteenService(CanteenRepository canteenRepository, DebtorRepository debtorRepository) {
        this.canteenRepository = canteenRepository;
        this.debtorRepository = debtorRepository;
    }

    public Canteen createCanteen(String food, String description, double valueSold, Date dateCant, double expenses, String annotations, List<DebtorDTO> debtorDTO) {
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
        insertCanteen.setProfit(profit);


        for(DebtorDTO dto : debtorDTO) {
            Debtors debtors = new Debtors();
            debtors.setNameDebtors(dto.getName());
            debtors.setCanteen(insertCanteen);
            debtors.setAmount(dto.getAmount());
            insertCanteen.getDebtors().add(debtors);
        }
        return canteenRepository.save(insertCanteen);
    }
    public List<Canteen> findAllCanteens() {
        return canteenRepository.findAll();
    }
    public Canteen findByDateCant(Date dateCant) {

        return canteenRepository.findByDateCant(dateCant)
                .orElseThrow(() -> ResourceNotFoundException.CanteenNotFound(dateCant));
    }
    public List<DebtorWithCanteenDTO> findDebtorsWithCanteenInfo(String nameDebtors){
        List<Debtors> debtors = debtorRepository.findByName(nameDebtors);

        if(debtors.isEmpty()){
            throw new ResourceNotFoundException("Nenhum devedor encontrado com este nome: " + nameDebtors);
        }

        return debtors.stream()
                .map(debt -> new DebtorWithCanteenDTO(debt.getNameDebtors(), debt.getAmount(), debt.getCanteen().getDateCant()))
                .collect(Collectors.toList());
    }

}
