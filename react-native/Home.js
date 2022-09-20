import React  from "react";
import {View, Image, Text,StyleSheet} from 'react-native'


const Home =(props) => {
    return (
            <View style={{flexDirection:'column'}}>
                
                <Text style={styles.title}>Welcome to glob</Text>
                <Text style={styles.subtitle}>{props.username}</Text>
                <View style={styles.textContainer}>
                    <Text style={styles.content}>{introText}</Text>
                </View>
            </View>



    );   
}

const introText=`asdk asdfasdhfoihasdfoih asdflka sdlfkj asdif asdf`


const styles=StyleSheet.create({
container:    
{},
textContainer:{},
title:{},
subtitle:{},
container:{}


});

export default Home;