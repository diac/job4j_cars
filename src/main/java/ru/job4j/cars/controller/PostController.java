package ru.job4j.cars.controller;

import lombok.AllArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.job4j.cars.enumeration.SteeringWheelSide;
import ru.job4j.cars.model.Car;
import ru.job4j.cars.model.Post;
import ru.job4j.cars.model.User;
import ru.job4j.cars.service.*;
import ru.job4j.cars.util.Cars;
import ru.job4j.cars.util.DateFormat;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.IntStream;

@Controller
@AllArgsConstructor
public class PostController {

    private final PostService postService;
    private final CarService carService;
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
        model.addAttribute("post", new Post());
        return "posts/new";
    }

    @PostMapping("/posts/create")
    public String store(
        @ModelAttribute("post") Post post,
        @RequestParam("photoUpload") MultipartFile photoUpload,
        @RequestParam("carBrandId") int carBrandId,
        @RequestParam("carModel") String carModel,
        @RequestParam("carBodyStyleId") int carBodyStyleId,
        @RequestParam("carExteriorColorId") int carExteriorColorId,
        @RequestParam("carTransmissionTypeId") int carTransmissionTypeId,
        @RequestParam("carDrivetrainId") int carDrivetrainId,
        @RequestParam("carSteeringWheelSide") SteeringWheelSide carSteeringWheelSide,
        @RequestParam("carEngineTypeId") int carEngineTypeId,
        @RequestParam("carEngineVolumeId") int carEngineVolumeId,
        @RequestParam("carHorsepower") int carHorsepower,
        @RequestParam("carProductionYear") int carProductionYear,
        HttpServletRequest request,
        RedirectAttributes redirectAttributes
    ) throws IOException {
        Car car = new Car(
                0,
                bodyStyleService.findById(carBodyStyleId).orElse(null),
                exteriorColorService.findById(carExteriorColorId).orElse(null),
                transmissionTypeService.findById(carTransmissionTypeId).orElse(null),
                drivetrainService.findById(carDrivetrainId).orElse(null),
                carSteeringWheelSide,
                carModel,
                engineTypeService.findById(carEngineTypeId).orElse(null),
                engineVolumeService.findById(carEngineVolumeId).orElse(null),
                brandService.findById(carBrandId).orElse(null),
                carHorsepower,
                carProductionYear,
                new HashSet<>()
        );
        Optional<Car> carInDb = carService.add(car);
        if (carInDb.isEmpty()) {
            redirectAttributes.addFlashAttribute(
                    "errorMessage",
                    "Не удалось сохранить данные автомобиля"
            );
            return "redirect:/my_posts";
        }
        post.setCar(carInDb.get());
        post.setPhoto(photoUpload.getBytes());
        post.setUser((User) request.getSession().getAttribute("user"));
        Optional<Post> postInDb = postService.add(post);
        if (postInDb.isEmpty()) {
            redirectAttributes.addFlashAttribute(
                    "errorMessage",
                    "Не удалось сохранить данные объявления"
            );
        }
        return "redirect:/my_posts";
    }

    @GetMapping("/my_posts")
    public String userPosts(Model model, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        model.addAttribute("posts", postService.findAllByUserId(user.getId()));
        model.addAttribute("carDisplayNameHelper", Cars.displayNameHelper());
        model.addAttribute("dateFormat", DateFormat.defaultFormatter());
        return "posts/myPosts";
    }

    @GetMapping("/posts/photo/{postId}")
    public ResponseEntity<Resource> postPhoto(@PathVariable("postId") Integer postId) {
        ResponseEntity<Resource> responseEntity;
        Post post = postService.findById(postId).orElse(new Post());
        responseEntity = ResponseEntity.ok()
                .headers(new HttpHeaders())
                .contentLength(post.getPhoto().length)
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new ByteArrayResource(post.getPhoto()));
        return responseEntity;
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