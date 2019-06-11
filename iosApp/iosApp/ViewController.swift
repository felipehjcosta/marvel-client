//
//  ViewController.swift
//  iosApp
//
//  Created by Felipe Costa on 09/06/19.
//  Copyright © 2019 Felipe Costa. All rights reserved.
//

import UIKit
import library

class ViewController: UIViewController {

    @IBOutlet weak var contentLabel: UILabel!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        let marvelPrivateKey = ProcessInfo.processInfo.environment["MARVEL_PRIVATE_KEY"]!
        let marvelPublicKey = ProcessInfo.processInfo.environment["MARVEL_PUBLIC_KEY"]!
        
        let applicationApi = ApplicationApiKt.createApplicationApi(marvelPrivateKey: marvelPrivateKey, marvelPublicKey: marvelPublicKey)
        applicationApi.fetchCharacters { (result) -> KotlinUnit in
            DispatchQueue.main.async {
                self.contentLabel.text = result
            }
            return KotlinUnit()
        }
    }
}
