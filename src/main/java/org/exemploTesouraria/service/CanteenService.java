package org.exemploTesouraria.service;

import org.exemploTesouraria.DTO.CanteenDTO;
import org.exemploTesouraria.DTO.DebtorDTO;
import org.exemploTesouraria.DTO.DebtorWithCanteenDTO;
import org.exemploTesouraria.exception.DataConflictException;
import org.exemploTesouraria.exception.ResourceNotFoundException;
import org.exemploTesouraria.model.Canteen;
import org.exemploTesouraria.model.Debtors;
import org.exemploTesouraria.repository.CanteenRepository;
import org.exemploTesouraria.repository.DebtorRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

    public CanteenDTO createCanteen(String food, String description, double valueSold, LocalDate dateCant, double expenses, String annotations, List<DebtorDTO> debtorDTO) {
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
            debtors.setNameDebtors(dto.name());
            debtors.setCanteen(insertCanteen);
            debtors.setAmount(dto.amount());
            insertCanteen.getDebtors().add(debtors);
        }
        Canteen saved = canteenRepository.save(insertCanteen);
        return CanteenDTO.fromEntity(saved);
    }
    public List<CanteenDTO> findAllCanteens() {
        return canteenRepository.findAll()
                .stream()
                .map(CanteenDTO::fromEntity)
                .collect(Collectors.toList());
    }
    public CanteenDTO findByDateCant(LocalDate dateCant) {
        Canteen canteen = canteenRepository.findByDateCant(dateCant)
                .orElseThrow(() -> ResourceNotFoundException.CanteenNotFound(dateCant));
        return CanteenDTO.fromEntity(canteen);
    }
    public List<CanteenDTO> findByMonth(int month){
        int year = LocalDate.now().getYear(); //pego o ano atual do sistema
        LocalDate start = LocalDate.of(year ,month,1); //crio a data de inicio do mes
        LocalDate end = start.withDayOfMonth(start.lengthOfMonth()); //determina o ultimo dia do mes, com a funcao lengthOfMonth pegando a quantidade de dias do mes

        return canteenRepository.findByDateCantBetween(start, end)
                .stream()
                .map(CanteenDTO::fromEntity)
                .collect(Collectors.toList());
    }
    public List<DebtorWithCanteenDTO> findDebtorsWithCanteenInfo(String nameDebtors){
        List<Debtors> debtors = debtorRepository.findByNameDebtors(nameDebtors);

        if(debtors.isEmpty()){
            throw new ResourceNotFoundException("Nenhum devedor encontrado com este nome: " + nameDebtors);
        }

        return debtors.stream()
                .map(debt -> new DebtorWithCanteenDTO(debt.getNameDebtors(), debt.getAmount(), debt.getCanteen().getDateCant()))
                .collect(Collectors.toList());
    }

}
