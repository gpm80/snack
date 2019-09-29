package ru.vkhackathon.snack.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vkhackathon.snack.Brand;
import ru.vkhackathon.snack.GpsPoint;
import ru.vkhackathon.snack.Trc;
import ru.vkhackathon.snack.common.GpsCalculator;
import ru.vkhackathon.snack.domain.TrcDAO;
import ru.vkhackathon.snack.repository.TrcRepository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Petr Gusarov
 */
@Service
public class TrcService {

    @Autowired
    private TrcRepository trcRepository;

    public List<Trc> findNearByPoing(GpsPoint point, int radius) {
        List<TrcDAO> trcNearPoint = trcRepository.findByNearPoint(point, radius);
        return trcNearPoint.stream()
                .map(TrcDAO::syncGetBean)
                .filter(bean -> GpsCalculator.isInSquareArea(bean, point, radius))
                .collect(Collectors.toList());
    }

    public Trc save(Trc trc){
        TrcDAO save = trcRepository.save(trc);
        return save.syncGetBean();
    }
}
