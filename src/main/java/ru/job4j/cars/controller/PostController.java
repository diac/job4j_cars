package ru.job4j.cars.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.job4j.cars.enumeration.SteeringWheelSide;
import ru.job4j.cars.service.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

@Controller
@AllArgsConstructor
public class PostController {

    private final PostService postService;
    private final BodyStyleService bodyStyleService;
    private final BrandService brandService;
    private final DrivetrainService drivetrainService;
    private final EngineTypeService engineTypeService;
    private final EngineVolumeService engineVolumeService;
    private final ExteriorColorService exteriorColorService;
    private final TransmissionTypeService transmissionTypeService;

    @GetMapping("/posts/new")
    public String addPost(Model model) {
        initEditorUiModel(model);
        return "posts/new";
    }

    private void initEditorUiModel(Model model) {
        model.addAttribute("bodyStyles", bodyStyleService.findAll());
        model.addAttribute("brands", brandService.findAll());
        model.addAttribute("drivetrains", drivetrainService.findAll());
        model.addAttribute("engineTypes", engineTypeService.findAll());
        model.addAttribute("engineVolumes", engineVolumeService.findAll());
        model.addAttribute("exteriorColors", exteriorColorService.findAll());
        model.addAttribute("transmissionTypes", transmissionTypeService.findAll());
        model.addAttribute("steeringWheelSides", SteeringWheelSide.values());
        final int year = LocalDate.now().getYear();
        List<Integer> yearRange = new ArrayList<>(
                IntStream.rangeClosed(year - 50, year)
                        .boxed()
                        .toList()
        );
        Collections.reverse(yearRange);
        model.addAttribute("productionYearRange", yearRange);
    }
}