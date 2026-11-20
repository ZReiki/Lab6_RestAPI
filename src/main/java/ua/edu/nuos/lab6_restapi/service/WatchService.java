package ua.edu.nuos.lab6_restapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.edu.nuos.lab6_restapi.model.Watch;
import ua.edu.nuos.lab6_restapi.repository.WatchRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WatchService {
    private final WatchRepository watchRepository;

    public List<Watch> findAll() {
        return watchRepository.findAll();
    }

    public Optional<Watch> findById(Long id) {
        return watchRepository.findById(id);
    }

    public Watch create(Watch watch) {
        watch.setWatchId(null);
        return watchRepository.save(watch);
    }

    public Optional<Watch> update(Long id, Watch updated) {
        return watchRepository.findById(id).map(existing -> {
            existing.setBrand(updated.getBrand());
            existing.setReleaseDate(updated.getReleaseDate());
            existing.setType(updated.getType());
            existing.setPrice(updated.getPrice());
            existing.setCaseDiameter(updated.getCaseDiameter());
            existing.setQuantity(updated.getQuantity());
            existing.setWarrantyMonths(updated.getWarrantyMonths());
            existing.setManufacturerId(updated.getManufacturerId());
            return watchRepository.save(existing);
        });
    }

    public boolean deleteById(Long id) {
        if (!watchRepository.existsById(id)) {
            return false;
        }
        watchRepository.deleteById(id);
        return true;
    }
}
