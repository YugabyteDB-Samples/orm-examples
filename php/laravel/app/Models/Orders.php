<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Orders extends Model
{
    public $timestamps = false;
    // protected $primaryKey = 'order_id';
    use HasFactory;

    protected $fillable = [
        'user_id',
        'order_total'
    ];

    public function orderLines() 
    {
        return $this->hasMany(OrderLines::class);
    }
}
