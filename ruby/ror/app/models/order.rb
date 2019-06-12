class Order < ApplicationRecord
  validates :orderTotal, presence: true
  validates :userId, presence: true
end
