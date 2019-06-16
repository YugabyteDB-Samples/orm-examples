class CreateProducts < ActiveRecord::Migration[5.2]
  def change
    create_table :products, primary_key: :productId do |t|
      t.string :productName
      t.string :description
      t.decimal :price
      t.timestamps
    end
  end
end
