package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.dto.PostSearchParams;
import ru.job4j.cars.model.*;
import ru.job4j.cars.repository.PostRepository;
import ru.job4j.cars.repository.SalesOrderRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Сервис, осуществляющий доступ к данным объектов модели Post в репозитории
 */
@Service
@AllArgsConstructor
public class SimplePostService implements PostService {

    private final PostRepository postRepository;

    private final UserService userService;

    private final SalesOrderRepository salesOrderRepository;

    /**
     * Получить список всех объявлений
     *
     * @return Список объявлений. Пустой список, если ничего не найдено.
     */
    @Override
    public List<Post> findAll() {
        return postRepository.findAll();
    }

    /**
     * Получить список всех объявлений по ID пользователя
     *
     * @param userId ID пользователя
     * @return Список объявлений. Пустой список, если ничего не найдено.
     */
    @Override
    public List<Post> findAllByUserId(int userId) {
        return postRepository.findAllByUserId(userId);
    }

    /**
     * Найти объявление по ID
     *
     * @param id ID объявления
     * @return объявление
     */
    @Override
    public Optional<Post> findById(int id) {
        return postRepository.findById(id);
    }

    /**
     * Найти объявление по ID, включая данные подписанных на объявление пользователей
     *
     * @param id ID объявления
     * @return Список объявлений. Пустой список, если ничего не найдено.
     */
    @Override
    public Optional<Post> findByIdWithParticipates(int id) {
        return postRepository.findByIdWithParticipates(id);
    }

    /**
     * Сохранить объявление
     *
     * @param post объявление
     * @return Объявление с id
     */
    @Override
    public Optional<Post> add(Post post) {
        post.setCreated(LocalDateTime.now());
        post.setAvailable(true);
        return postRepository.add(post);
    }

    /**
     * Обновить объявление
     *
     * @param post Объявление
     * @param user Пользователь, который пытается обновить объявление
     * @return true в случае удачного обновления. Иначе -- false
     * @throws IllegalArgumentException В случае, если пользователь попытается обновить чужое объявление
     */
    @Override
    public boolean update(Post post, User user) throws IllegalArgumentException {
        if (!user.equals(post.getUser())) {
            throw new IllegalArgumentException(String.format(
                    "Не удалось обновить объявление. Пользователь %s не является автором объявления %d",
                    user.getLogin(),
                    post.getId()
            ));
        }
        return postRepository.update(post);
    }

    /**
     * Обновить объявление по ID
     *
     * @param id             ID объявления
     * @param newDescription Новое описание объявления
     * @param newPhoto       Новая фотография объявления
     * @param newPrice       Новая цена в объявлении
     * @param user           Пользователь, который пытается обновить объявление
     * @return true в случае удачного обновления. Иначе -- false
     * @throws IllegalArgumentException В случае, если пользователь попытается обновить чужое объявление
     */
    @Override
    public boolean update(
            int id,
            String newDescription,
            byte[] newPhoto,
            int newPrice,
            User user
    ) throws IllegalArgumentException {
        boolean success = false;
        Optional<Post> postInDb = findById(id);
        if (postInDb.isPresent()) {
            postInDb.get().setDescription(newDescription);
            postInDb.get().setPhoto(newPhoto);
            postInDb.get().setPrice(newPrice);
            success = update(postInDb.get(), user);
        }
        return success;
    }

    /**
     * Удалить объявление
     *
     * @param post Объявление
     * @param user Пользователь, который пытается удалить объявление
     * @return true в случае удачного удаления. Иначе -- false
     * @throws IllegalArgumentException В случае, если пользователь попытается удалить чужое объявление
     */
    @Override
    public boolean delete(Post post, User user) {
        if (!user.equals(post.getUser())) {
            throw new IllegalArgumentException(String.format(
                    "Не удалось удалить объявление. Пользователь %s не является автором объявления %d",
                    user.getLogin(),
                    post.getId()
            ));
        }
        return postRepository.delete(post);
    }

    /**
     * Удалить объявление по ID
     *
     * @param id   ID объявления
     * @param user Пользователь, который пытается удалить объявление
     * @return true в случае удачного удаления. Иначе -- false
     * @throws IllegalArgumentException В случае, если пользователь попытается удалить чужое объявление
     */
    @Override
    public boolean delete(int id, User user) {
        Post post = postRepository.findById(id).orElse(new Post());
        if (!user.equals(post.getUser())) {
            throw new IllegalArgumentException(String.format(
                    "Не удалось удалить объявление. Пользователь %s не является автором объявления %d",
                    user.getLogin(),
                    id
            ));
        }
        return postRepository.delete(id);
    }

    /**
     * Найти все объявления в диапазоне дат
     *
     * @param dateFrom Минимальное значение диапазона дат
     * @param dateTo   Максимальное значение диапазона дат
     * @return Список объявлений. Пустой список, если ничего не найдено
     */
    @Override
    public List<Post> findAllInDateRange(LocalDateTime dateFrom, LocalDateTime dateTo) {
        return postRepository.findAllInDateRange(dateFrom, dateTo);
    }

    /**
     * Получить все объявления с фото
     *
     * @return Список объявлений. Пустой список, если ничего не найдено
     */
    @Override
    public List<Post> findAllWithPhoto() {
        return postRepository.findAllWithPhoto();
    }

    /**
     * Получить все объявления для определенной машины
     *
     * @param car Машина, для которой ищутся объявления
     * @return Список объявлений. Пустой список, если ничего не найдено
     */
    @Override
    public List<Post> findAllByCar(Car car) {
        return postRepository.findAllByCar(car);
    }

    /**
     * Деактивировать объявление по ID
     *
     * @param id   ID объявления
     * @param user Пользователь, который пытается деактивировать объявление
     * @return true в случае удачной деактивации. Иначе -- false
     */
    @Override
    public boolean deactivate(int id, User user) {
        Post post = postRepository.findById(id).orElse(new Post());
        post.setAvailable(false);
        return update(post, user);
    }

    /**
     * Найти все объявления, удовлетворяющие условиям поиска
     *
     * @param postSearchParams Объект, содержащий условия поиска
     * @return Список объявлений. Пустой список, если ничего не найдено
     */
    @Override
    public List<Post> search(PostSearchParams postSearchParams) {
        return postRepository.search(postSearchParams);
    }

    /**
     * Подписать пользователя на объявление
     *
     * @param postId          ID объявления
     * @param participantUser Пользователь
     * @return true в случае успешного обновления данных. Иначе -- false
     */
    @Override
    public boolean addParticipant(int postId, User participantUser) {
        Optional<Post> postInDb = postRepository.findByIdWithParticipates(postId);
        if (postInDb.isEmpty()) {
            throw new IllegalArgumentException(String.format("Объявление %d не существует", postId));
        }
        Post post = postInDb.get();
        post.getParticipates().add(participantUser);
        return postRepository.update(post);
    }

    /**
     * Завершить сделку в рамках объявления
     *
     * @param postId   ID объявления
     * @param sellerId ID продавца
     * @param buyerId  ID покупателя
     * @return true в случае успешного обновления данных. Иначе -- false
     * @throws IllegalStateException В случае, если сделка закрывается от лица пользователя, которому не принадлежит объявление
     */
    @Override
    public boolean finalizeSalesOrder(int postId, int sellerId, int buyerId) throws IllegalStateException {
        Post post = postRepository.findById(postId).orElse(new Post());
        User seller = userService.findById(sellerId).orElse(new User());
        User buyer = userService.findById(buyerId).orElse(new User());
        if (!post.getUser().equals(seller)) {
            throw new IllegalStateException(
                    String.format("Ошибка доступа. Пользователь %d не является владельцем объявления %d", sellerId, postId)
            );
        }
        post.setAvailable(false);
        postRepository.update(post);
        SalesOrder salesOrder = new SalesOrder(0, post, seller, buyer, LocalDateTime.now());
        Optional<SalesOrder> salesOrderInDb = salesOrderRepository.add(salesOrder);
        return salesOrderInDb.isPresent();
    }
}