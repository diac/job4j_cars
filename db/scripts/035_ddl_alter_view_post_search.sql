DROP VIEW post_search;
CREATE VIEW
    post_search
AS
SELECT
	Post.id AS post_id,
	Post.price,
	Car.id AS car_id,
	Car.body_style_id,
	Car.exterior_color_id,
	Car.transmission_type_id,
	Car.drivetrain_id,
	Car.steering_wheel_side,
	Car.model_name,
	Car.engine_type_id,
	Car.brand_id,
	Car.horsepower,
	Car.production_year,
	Car.engine_volume,
	Car.kilometrage
FROM
	auto_post AS Post
INNER JOIN
	car AS Car ON Car.id = Post.car_id;