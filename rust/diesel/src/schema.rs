table! {
    order_lines (order_line_id) {
        order_line_id -> Int4,
        order_id -> Int4,
        product_id -> Int4,
        units -> Int2,
    }
}

table! {
    orders (order_id) {
        order_id -> Int4,
        order_total -> Numeric,
        user_id -> Int4,
    }
}

table! {
    products (product_id) {
        product_id -> Int4,
        product_name -> Varchar,
        product_description -> Varchar,
        price -> Numeric,
    }
}

table! {
    users (user_id) {
        user_id -> Int4,
        first_name -> Varchar,
        last_name -> Varchar,
        user_email -> Varchar,
    }
}

joinable!(order_lines -> orders (order_id));
joinable!(order_lines -> products (product_id));
joinable!(orders -> users (user_id));

allow_tables_to_appear_in_same_query!(order_lines, orders, products, users,);
