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
import ru.job4j.cars.dto.PostSearchParams;
import ru.job4j.cars.model.User;
import ru.job4j.cars.service.*;
import ru.job4j.cars.util.Cars;
import ru.job4j.cars.util.DateFormat;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.IntStream;

@Controller
@AllArgsConstructor
public class PostController {

    private static final int ENGINE_VOLUME_MIN = 200;
    private static final int ENGINE_VOLUME_MAX = 10_000;
    private static final int ENGINE_VOLUME_STEP = 100;
    private static final int ENGINE_VOLUME_DIVIDER = 1_000;

    private final PostService postService;
    private final CarService carService;
    private final BodyStyleService bodyStyleService;
    private final BrandService brandService;
    private final DrivetrainService drivetrainService;
    private final EngineTypeService engineTypeService;
    private final ExteriorColorService exteriorColorService;
    private final TransmissionTypeService transmissionTypeService;

    @GetMapping("/")
    public String index(
            Model model,
            @RequestParam(name = "carBrandId", required = false) Integer carBrandId,
            @RequestParam(name = "priceMin", required = false) Integer priceMin,
            @RequestParam(name = "priceMax", required = false) Integer priceMax,
            @RequestParam(name = "carModel", required = false) String carModel,
            @RequestParam(name = "carBodyStyleId", required = false) Integer carBodyStyleId,
            @RequestParam(name = "carExteriorColorId", required = false) Integer carExteriorColorId,
            @RequestParam(name = "carTransmissionTypeId", required = false) Integer carTransmissionTypeId,
            @RequestParam(name = "carDrivetrainId", required = false) Integer carDrivetrainId,
            @RequestParam(name = "carSteeringWheelSide", required = false) SteeringWheelSide carSteeringWheelSide,
            @RequestParam(name = "carEngineTypeId", required = false) Integer carEngineTypeId,
            @RequestParam(name = "carEngineVolumeMin", required = false) Integer carEngineVolumeMin,
            @RequestParam(name = "carEngineVolumeMax", required = false) Integer carEngineVolumeMax,
            @RequestParam(name = "carHorsepowerMin", required = false) Integer carHorsepowerMin,
            @RequestParam(name = "carHorsepowerMax", required = false) Integer carHorsepowerMax,
            @RequestParam(name = "carProductionYearMin", required = false) Integer carProductionYearMin,
            @RequestParam(name = "carProductionYearMax", required = false) Integer carProductionYearMax,
            @RequestParam(name = "carKilometrageMax", required = false) Integer carKilometrageMax
    ) {
        initUiModel(model);
        PostSearchParams postSearchParams = new PostSearchParams(
                priceMin,
                priceMax,
                carBrandId,
                carModel,
                carBodyStyleId,
                carExteriorColorId,
                carTransmissionTypeId,
                carDrivetrainId,
                carSteeringWheelSide,
                carEngineTypeId,
                carEngineVolumeMin,
                carEngineVolumeMax,
                carHorsepowerMin,
                carHorsepowerMax,
                carProductionYearMin,
                carProductionYearMax,
                carKilometrageMax
        );
        model.addAttribute("posts", postService.search(postSearchParams));
        model.addAttribute("postSearchParams", postSearchParams);
        return "posts/index";
    }

    @GetMapping("/posts/new")
    public String addPost(Model model) {
        initUiModel(model);
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
            @RequestParam("carEngineVolume") int carEngineVolume,
            @RequestParam("carHorsepower") int carHorsepower,
            @RequestParam("carProductionYear") int carProductionYear,
            @RequestParam("carKilometrage") int carKilometrage,
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
                carEngineVolume,
                brandService.findById(carBrandId).orElse(null),
                carHorsepower,
                carProductionYear,
                carKilometrage,
                new HashSet<>()
        );
        Optional<Car> carInDb = carService.add(car);
        if (carInDb.isEmpty()) {
            redirectAttributes.addFlashAttribute(
                    "errorMessage",
                    "???? ?????????????? ?????????????????? ???????????? ????????????????????"
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
                    "???? ?????????????? ?????????????????? ???????????? ????????????????????"
            );
        } else {
            redirectAttributes.addFlashAttribute(
                    "successMessage",
                    "?????????? ???????????????????? ?????????????? ??????????????????"
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

    @GetMapping("/posts/{id}/edit")
    public String edit(
            @PathVariable("id") int id,
            Model model,
            HttpServletRequest request,
            RedirectAttributes redirectAttributes
    ) {
        Optional<Post> post = postService.findById(id);
        if (post.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "???????????????????? ???? ??????????????");
            return "redirect:/my_posts";
        }
        User user = (User) request.getSession().getAttribute("user");
        if (!post.get().getUser().equals(user)) {
            redirectAttributes.addFlashAttribute("errorMessage", "???????????? ??????????????");
            return "redirect:/my_posts";
        }
        model.addAttribute("post", post.get());
        return "posts/edit";
    }

    @PatchMapping("/posts/{id}")
    public String patch(
            @ModelAttribute("post") Post post,
            @PathVariable("id") int id,
            @RequestParam("photoUpload") MultipartFile photoUpload,
            HttpServletRequest request,
            RedirectAttributes redirectAttributes
    ) throws IOException {
        User user = (User) request.getSession().getAttribute("user");
        try {
            postService.update(id, post.getDescription(), photoUpload.getBytes(), post.getPrice(), user);
            redirectAttributes.addFlashAttribute(
                    "successMessage",
                    "???????????? ???????????????????? ??????????????????"
            );
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute(
                    "errorMessage",
                    String.format("???? ?????????????? ???????????????? ????????????????????. ??????????????: %s", e.getMessage())
            );
        }
        return "redirect:/my_posts";
    }

    @GetMapping("/posts/{id}/deactivate")
    public String deactivateView(
            @PathVariable("id") int id,
            Model model,
            HttpServletRequest request,
            RedirectAttributes redirectAttributes
    ) {
        Optional<Post> post = postService.findById(id);
        if (post.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "???????????????????? ???? ??????????????");
            return "redirect:/my_posts";
        }
        User user = (User) request.getSession().getAttribute("user");
        if (!post.get().getUser().equals(user)) {
            redirectAttributes.addFlashAttribute("errorMessage", "???????????? ??????????????");
            return "redirect:/my_posts";
        }
        model.addAttribute("post", post.get());
        return "posts/deactivate";
    }

    @PatchMapping("/posts/{id}/deactivate")
    public String deactivate(
            @PathVariable("id") int id,
            HttpServletRequest request,
            RedirectAttributes redirectAttributes
    ) {
        User user = (User) request.getSession().getAttribute("user");
        try {
            postService.deactivate(id, user);
            redirectAttributes.addFlashAttribute(
                    "successMessage",
                    "???????????????????? ????????????????????????????"
            );
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute(
                    "errorMessage",
                    String.format("???? ?????????????? ???????????????????????????? ????????????????????. ??????????????: %s", e.getMessage())
            );
        }
        return "redirect:/my_posts";
    }

    @GetMapping("/posts/photo/{postId}")
    public ResponseEntity<Resource> postPhoto(@PathVariable("postId") Integer postId) throws IOException {
        ByteArrayResource imageBytes;
        try (
                InputStream imgInputStream = PostController.class.getClassLoader()
                        .getResourceAsStream("static/img/no-image-available.png")
        ) {
            imageBytes = new ByteArrayResource(
                    imgInputStream
                            .readAllBytes()
            );
        }
        ResponseEntity<Resource> responseEntity;
        Post post = postService.findById(postId).orElse(new Post());
        if (post.getPhoto() != null) {
            imageBytes = new ByteArrayResource(post.getPhoto());
        }
        responseEntity = ResponseEntity.ok()
                .headers(new HttpHeaders())
                .contentLength(imageBytes.contentLength())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(imageBytes);
        return responseEntity;
    }

    @GetMapping("/posts/{id}")
    public String view(
            @PathVariable("id") int id,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        Optional<Post> post = postService.findById(id);
        if (post.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "???????????????????? ???? ??????????????");
            return "redirect:/";
        }
        model.addAttribute("post", post.get());
        return "posts/view";
    }

    @PostMapping("/posts/{id}/participate")
    public String participate(
            @PathVariable("id") int id,
            HttpServletRequest request,
            RedirectAttributes redirectAttributes
    ) {
        User user = (User) request.getSession().getAttribute("user");
        try {
            postService.addParticipant(id, user);
            redirectAttributes.addFlashAttribute(
                    "successMessage",
                    "???? ???????????? ???????????? ???? ???????????????????? ????????????. ???????????????? ???????????????? ?? ????????"
            );
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute(
                    "errorMessage",
                    "???? ?????????????? ???????????? ????????????"
            );
        }
        return "redirect:/";
    }

    @GetMapping("/posts/{id}/participates")
    public String participates(@PathVariable("id") int id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Post> post = postService.findByIdWithParticipates(id);
        if (post.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "???????????????????? ???? ??????????????");
            return "redirect:/";
        }
        model.addAttribute("post", post.get());
        return "/posts/participates";
    }

    @PostMapping("/posts/finalize/")
    public String finalizeSalesOrder(
            @RequestParam("buyerId") int buyerId,
            @RequestParam("postId") int postId,
            HttpServletRequest request,
            RedirectAttributes redirectAttributes
    ) {
        Optional<Post> post = postService.findById(postId);
        if (post.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "???????????????????? ???? ??????????????");
            return "redirect:/my_posts";
        }
        User user = (User) request.getSession().getAttribute("user");
        if (!post.get().getUser().equals(user)) {
            redirectAttributes.addFlashAttribute("errorMessage", "???????????? ??????????????");
            return "redirect:/my_posts";
        }
        try {
            postService.finalizeSalesOrder(postId, user.getId(), buyerId);
            redirectAttributes.addFlashAttribute("successMessage", "???????????? ??????????????????");
        } catch (IllegalStateException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/my_posts";
    }

    private void initUiModel(Model model) {
        model.addAttribute("bodyStyles", bodyStyleService.findAll());
        model.addAttribute("brands", brandService.findAll());
        model.addAttribute("drivetrains", drivetrainService.findAll());
        model.addAttribute("engineTypes", engineTypeService.findAll());
        model.addAttribute("exteriorColors", exteriorColorService.findAll());
        model.addAttribute("transmissionTypes", transmissionTypeService.findAll());
        model.addAttribute("steeringWheelSides", SteeringWheelSide.values());
        final Map<Integer, String> engineVolumeRange = new TreeMap<>();
        for (int volume = ENGINE_VOLUME_MIN; volume <= ENGINE_VOLUME_MAX; volume += ENGINE_VOLUME_STEP) {
            engineVolumeRange.put(
                    volume,
                    (float) volume / ENGINE_VOLUME_DIVIDER + " ??."
            );
        }
        model.addAttribute("engineVolumeRange", engineVolumeRange);
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